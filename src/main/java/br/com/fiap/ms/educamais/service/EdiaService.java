package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.PerguntaEdiaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.MensagemEdiaResponseDTO;
import br.com.fiap.ms.educamais.dto.response.RespostaEdiaResponseDTO;
import br.com.fiap.ms.educamais.entities.AutorMensagem;
import br.com.fiap.ms.educamais.entities.MensagemEdia;
import br.com.fiap.ms.educamais.entities.Usuario;
import br.com.fiap.ms.educamais.repository.MensagemEdiaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EdiaService {

    private static final String FALLBACK =
            "Não encontrei essa informação no material oficial indexado do Instituto Eurofarma. "
            + "Como só respondo com base em conteúdo que consigo citar, prefiro não responder a arriscar uma informação errada. "
            + "Tente reformular usando um termo do curso, ou fale com o instrutor responsável pela trilha.";

    private static final List<RegraResposta> REGRAS = List.of(
            new RegraResposta(
                    List.of("bpf", "contamina", "boas praticas"),
                    "A prevenção de contaminação cruzada começa pela segregação física das áreas e pelo controle de fluxo de "
                    + "pessoas e materiais. Áreas de pesagem de ativos exigem cabine de fluxo unidirecional, pressão diferencial "
                    + "monitorada e registro de limpeza entre cada campanha. A regra prática é: nenhum insumo de um lote entra na "
                    + "área antes da liberação da limpeza do lote anterior, com dupla checagem registrada no logbook da sala.",
                    "Boas Práticas de Fabricação (BPF) > Módulo 2 > Aula 1"),

            new RegraResposta(
                    List.of("evento adverso", "notific", "farmacovigil", "reacao adversa"),
                    "Todo evento adverso identificado deve ser notificado ao setor de Farmacovigilância em até 24 horas do "
                    + "conhecimento do caso, mesmo quando a relação causal com o medicamento ainda não estiver estabelecida. "
                    + "A notificação precisa conter os quatro elementos mínimos: paciente identificável, notificador identificável, "
                    + "medicamento suspeito e evento observado. Casos graves seguem prazo reduzido e fluxo de escalonamento imediato.",
                    "Farmacovigilância na Prática > Módulo 1 > Aula 3"),

            new RegraResposta(
                    List.of("lgpd", "dado pessoal", "dados pessoais", "titular"),
                    "Na indústria farmacêutica, dados de saúde são dados pessoais sensíveis e exigem base legal específica, que "
                    + "raramente é o consentimento em contexto de farmacovigilância — costuma ser cumprimento de obrigação legal ou "
                    + "tutela da saúde. Antes de compartilhar qualquer relato com terceiros, aplique a anonimização ou a "
                    + "pseudonimização prevista no procedimento interno, e registre a operação no inventário de tratamento.",
                    "LGPD Aplicada à Indústria Farmacêutica > Módulo 2 > Aula 2"),

            new RegraResposta(
                    List.of("valida", "protocolo", "qualifica"),
                    "A validação de processos produtivos se apoia em três etapas encadeadas: desenho do processo, qualificação do "
                    + "processo e verificação continuada. O protocolo precisa ser aprovado antes da execução e definir critérios de "
                    + "aceitação numéricos, número de lotes consecutivos e os atributos críticos de qualidade monitorados. "
                    + "Desvio em qualquer critério interrompe a validação e abre investigação formal antes de qualquer nova tentativa.",
                    "Validação de Processos Produtivos > Módulo 1 > Aula 2"),

            new RegraResposta(
                    List.of("progresso", "meu curso", "quanto falta", "minhas trilhas"),
                    "Seu progresso é calculado pelas aulas que você concluiu em cada matrícula, não por tempo de acesso. "
                    + "Você acompanha o percentual de cada trilha na tela de cursos, e a próxima aula pendente aparece sempre no topo. "
                    + "Essa informação vem do seu histórico na plataforma, não do material de treinamento, por isso não tenho fonte para citar aqui.",
                    null),

            new RegraResposta(
                    List.of("sala", "estudo em grupo", "pomodoro", "mediador"),
                    "As salas de estudo são encontros síncronos curtos, mediados por alguém que já domina o conteúdo, com "
                    + "temporizador compartilhado. Você confirma participação pela tela da sala, e a vaga só é garantida enquanto "
                    + "houver capacidade disponível. Essa informação vem do funcionamento da plataforma, não do material de treinamento, "
                    + "por isso não tenho fonte para citar aqui.",
                    null));

    @Autowired
    private MensagemEdiaRepository mensagemEdiaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public RespostaEdiaResponseDTO responder(PerguntaEdiaRequestDTO requestDTO) {
        Usuario usuario = usuarioService.buscarUsuario(requestDTO.getUsuarioId());

        MensagemEdia pergunta = new MensagemEdia();
        pergunta.setTexto(requestDTO.getPergunta());
        pergunta.setAutor(AutorMensagem.ALUNO);
        pergunta.setFonte(null);
        pergunta.setDataHora(LocalDateTime.now());
        pergunta.setUsuario(usuario);
        pergunta = mensagemEdiaRepository.save(pergunta);

        RegraResposta regra = encontrarRegra(requestDTO.getPergunta());

        MensagemEdia resposta = new MensagemEdia();
        resposta.setTexto(regra == null ? FALLBACK : regra.texto());
        resposta.setAutor(AutorMensagem.EDIA);
        resposta.setFonte(regra == null ? null : regra.fonte());
        resposta.setDataHora(LocalDateTime.now());
        resposta.setUsuario(usuario);
        resposta = mensagemEdiaRepository.save(resposta);

        return new RespostaEdiaResponseDTO(pergunta, resposta);
    }

    @Transactional(readOnly = true)
    public List<MensagemEdiaResponseDTO> listarHistorico(Long usuarioId) {
        usuarioService.buscarUsuario(usuarioId);
        return mensagemEdiaRepository.findByUsuarioIdOrderByDataHoraAsc(usuarioId).stream()
                .map(MensagemEdiaResponseDTO::new)
                .toList();
    }

    @Transactional
    public void limparHistorico(Long usuarioId) {
        usuarioService.buscarUsuario(usuarioId);
        mensagemEdiaRepository.deleteByUsuarioId(usuarioId);
    }

    private RegraResposta encontrarRegra(String pergunta) {
        String normalizada = pergunta.toLowerCase();
        return REGRAS.stream()
                .filter(regra -> regra.palavrasChave().stream().anyMatch(normalizada::contains))
                .findFirst()
                .orElse(null);
    }

    private record RegraResposta(List<String> palavrasChave, String texto, String fonte) {
    }
}
