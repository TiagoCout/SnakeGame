package org.academiadecodigo.nanderthals;

import java.io.*;

public class HighScore {

    private String fileName = "RegisterScores.txt";
    private int highestScore = 0;

    public void readFile(int score) throws IOException {
        // Se o ficheiro existir verificar highsore
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int number = Integer.parseInt(line);
                if (number > highestScore) {
                    highestScore = number;
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Ainda não existe ficheiro, vou criar");
        } finally {
            writeScore(score);
        }
    }


    public void writeScore(int score) throws IOException {
        if(getHighestScore() < score){
            highestScore = score;
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(String.valueOf(score));
        bufferedWriter.newLine();

        bufferedWriter.close();

    }
    }

    public int getHighestScore() {
        return highestScore;
    }
}
    /*public int getHighestScore() throws IOException {

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        int highestScore = 0;
        while((line = bufferedReader.readLine()) != null){
            int number = Integer.parseInt(line);
            if(number > highestScore){
                highestScore = number;
            }
        }
        return highestScore;
}
}*/
