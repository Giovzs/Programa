import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuessNumberGame extends JFrame {
    private int numberToGuess;
    private int remainingAttempts;
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton restartButton;

    public GuessNumberGame() {
        // Configurações da janela principal
        setTitle("Adivinhe o Número");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Define a cor de fundo da janela

        // Configurações do painel superior
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.LIGHT_GRAY); // Define a cor de fundo do painel superior

        JLabel label = new JLabel("Digite um número entre 1 e 10:");
        label.setForeground(Color.BLACK); // Define a cor do texto
        inputField = new JTextField(10);
        JButton guessButton = new JButton("Adivinhar");
        guessButton.addActionListener(new GuessButtonListener());

        restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(new RestartButtonListener());
        restartButton.setEnabled(false);

        topPanel.add(label);
        topPanel.add(inputField);
        topPanel.add(guessButton);
        topPanel.add(restartButton);

        // Configurações da área de texto central
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE); // Define a cor de fundo da área de texto

        // Adiciona os painéis à janela principal
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Exibe a janela principal
        pack();
        setVisible(true);

        startNewGame();
    }

    private void startNewGame() {
        // Reinicia as variáveis e gera um novo número aleatório
        numberToGuess = (int) (Math.random() * 10) + 1;
        remainingAttempts = 3;

        inputField.setEditable(true);
        inputField.setText("");
        restartButton.setEnabled(false);
        outputArea.setText("");
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(inputField.getText());

                if (guess > 10 || guess < 1) {
                    outputArea.setForeground(Color.RED); // Define a cor do texto como vermelho
                    outputArea.append("O número digitado não é permitido. Digite um número entre 1 e 10.\n");
                    inputField.setText(""); // Limpa o campo de entrada
                } else {
                    if (guess == numberToGuess) {
                        outputArea.setForeground(Color.GREEN); // Define a cor do texto como verde
                        outputArea.append("Parabéns! Você acertou o número!\n");
                        inputField.setEditable(false);
                        restartButton.setEnabled(true);
                    } else {
                        remainingAttempts--;
                        if (remainingAttempts == 0) {
                            outputArea.setForeground(Color.RED); // Define a cor do texto como vermelho
                            outputArea.append("Errou! O número correto era " + numberToGuess + ".\n");
                            outputArea.append("Suas tentativas acabaram. O jogo terminou.\n");
                            inputField.setEditable(false);
                            restartButton.setEnabled(true);
                        } else {
                            outputArea.setForeground(Color.RED); // Define a cor do texto como vermelho
                            outputArea.append("Errou! Tente novamente. Tentativas restantes: " + remainingAttempts + "\n");
                        }
                    }

                    inputField.setText(""); // Limpa o campo de entrada
                }
            } catch (NumberFormatException ex) {
                outputArea.setForeground(Color.RED); // Define a cor do texto como vermelho
                outputArea.append("Entrada inválida. Digite um número válido.\n");
                inputField.setText(""); // Limpa o campo de entrada
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startNewGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessNumberGame();
            }
        });
    }
}
