package dev_marcelo.maNotes.infra.security.interface_grafica.notificacao;

import dev_marcelo.maNotes.entity.Notificacao;
import javafx.application.Platform;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class NotificationScheduler {

    private final Timer timer = new Timer(true);
    private final NotificacaoApiClient apiClient = new NotificacaoApiClient();
    private final Set<Long> notificacoesAgendadas = new HashSet<>();

    public void iniciar() {
        new Thread(() -> {
            try {
                List<Notificacao> notificacoes = apiClient.buscarNotificacao();
                for (Notificacao notificacao : notificacoes) {
                    if (!notificacoesAgendadas.contains(notificacao.getId())) {
                        agendarNotificacao(notificacao);
                        notificacoesAgendadas.add(notificacao.getId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erro ao agendar notificações: " + e.getMessage());
            }
        }).start();
    }

    public void agendarNovaNotificacao(Notificacao notificacao) {
        if (podeAgendar(notificacao)) {
            agendarNotificacao(notificacao);
        }
    }

    private boolean podeAgendar(Notificacao n) {
        return n.getDataHora() != null
                && n.getDataHora().isAfter(LocalDateTime.now())
                && !notificacoesAgendadas.contains(n.getId());
    }

    private void agendarNotificacao(Notificacao notificacao) {
        long delayMillis = Duration.between(LocalDateTime.now(), notificacao.getDataHora()).toMillis();
        if (delayMillis <= 0) return;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    PopupNotification.mostrar(notificacao.getTitulo(), notificacao.getDescricao());
                    // Se quiser, remove do set para permitir reedições e reagendamentos
                });
            }
        }, delayMillis);

        notificacoesAgendadas.add(notificacao.getId());
    }
}