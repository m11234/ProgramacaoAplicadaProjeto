package controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Classe para enviar emails
 * <p>
 * Aqui seguimos uma implementacao abaixo referenciada para implementar o envio de emails apos uma criacao conta
 * </p>
 */

public class EmailService {

    private static final String brevo_user = "";
    private static final String brevo_password = "";

    private static final String sender = "";

    /**
     * Aqui no metodo para enviar emails ele recebe os parametro abaixo mencionados e da return de true para confirmar
     * ao controller do registar conta que foi enviado o email do brevo
     * @param emailDestino
     * @param usernameDestino
     * @return true or false
     */

    public static boolean enviarEmailConfirmacao(String emailDestino, String usernameDestino) {

        /*
        Nota: Envio de email via protocolo de transferência.
        Solução adaptada da solucao do Mkyong "JavaMail API – Sending email via Gmail SMTP example".
        Fonte: https://mkyong.com/java/javamail-api-sending-email-via-gmail-smtp-example/
        */

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp-relay.brevo.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(brevo_user, brevo_password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject("Confirmação de criação de conta");

            message.setText("Olá " + usernameDestino + ",\n\n"
                    + "Conta criada com sucesso!\n\n"
                    + "Por favor espera que seja aprovada para fazer login.");

            Transport.send(message);
            System.out.println("Email enviado com sucesso para: " + emailDestino);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erro ao enviar email. Verifica se o EMAIL_REMETENTE está validado no Brevo.");
            return false;
        }
    }
}