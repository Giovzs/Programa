import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GuessNumberGame extends JFrame {
    private int numberToGuess;
    private int remainingAttempts;
    private JTextField inputField;
    private JTextArea outputArea;
    private JButton restartButton;
private String fileName = "C:/Users/giovanne.santos/Documents/NetBeansProjects/Programa/GuessNumberGame/data/saved_game.txt";



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

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new SaveButtonListener());

        restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(new RestartButtonListener());
        restartButton.setEnabled(false);

        topPanel.add(label);
        topPanel.add(inputField);
        topPanel.add(guessButton);
        topPanel.add(saveButton);
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
        if (isSavedGameAvailable()) {
            int choice = JOptionPane.showConfirmDialog(this, "Existe um jogo salvo. Deseja carregá-lo?", "Jogo Salvo", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                loadGame();
                return;
            }
        }

        numberToGuess = (int) (Math.random() * 10) + 1;
        remainingAttempts = 3;

        inputField.setEditable(true);
        inputField.setText("");
        restartButton.setEnabled(false);
        outputArea.setText("");
    }

    private void saveGame() {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(numberToGuess);
            printWriter.println(remainingAttempts);
            printWriter.close();
            outputArea.append("Jogo salvo.\n");
        } catch (IOException e) {
            outputArea.append("Erro ao salvar o jogo.\n");
            e.printStackTrace();
        }
    }

    private void loadGame() {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            numberToGuess = Integer.parseInt(bufferedReader.readLine());
            remainingAttempts = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
            outputArea.append("Jogo carregado.\n");
        } catch (FileNotFoundException e) {
            outputArea.append("Nenhum jogo salvo encontrado.\n");
        } catch (IOException e) {
            outputArea.append("Erro ao carregar o jogo.\n");
            e.printStackTrace();
        }
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

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            saveGame();
        }
    }

    private class RestartButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startNewGame();
        }
    }

    private boolean isSavedGameAvailable() {
        File file = new File(fileName);
        return file.exists();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessNumberGame();
            }
        });
    }
}
