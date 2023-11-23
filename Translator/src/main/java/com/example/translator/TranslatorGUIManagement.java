package com.example.translator;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class TranslatorGUIManagement {

    public static void insertDictGUIDataFromFile() throws IOException {
        try {
            //DictionaryGUI.dict.set(0,new Word("","",""));
            File file2 = new File("/Users/admin/Desktop/Translator/src/main/resources/dictionary.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
            StringBuilder target = new StringBuilder().append("DICTIONARY APP");
            StringBuilder res = new StringBuilder().append("");
            String spelling = "Nguyen Minh Hung && Nguyen Van Len";
            int targetEndIndex = 0;
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if(!line.isEmpty()) { // Neu ko co se bi loi index out of bounds
                    if (line.charAt(0) == '@') {
                        Word word = new Word(target.toString().trim(),spelling,res.toString());
                        TranslatorGUI.dict.add(word);
                        res = new StringBuilder();
                        target = new StringBuilder();
                        spelling = "";
                        targetEndIndex = line.length();

                        for (int i = 1; i < line.length(); i++) {
                            if (line.charAt(i) != '/') {
                                target.append(line.charAt(i));
                            } else {
                                targetEndIndex = i;
                                break;
                            }
                        }
                        spelling = line.substring(targetEndIndex);
                        //System.out.println(spelling);
                    } else {
                        res.append(line);
                        res.append(System.getProperty("line.separator")); // them dong moi
                    }

                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Please check file directory");
        }
    }

    public static void exportDictGUIDataToFile() throws IOException{
        File outfile = new File("src/main/resources/dictionary.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile)));
        ArrayList<Word> updatedDict = TranslatorGUI.dict;
        for (int i = 1; i < TranslatorGUI.dict.size(); i++) {
            bufferedWriter.write("@" + TranslatorGUI.dict.get(i).getWord_target());
            bufferedWriter.write(" " + TranslatorGUI.dict.get(i).getWord_spelling());
            bufferedWriter.newLine();
            bufferedWriter.write(TranslatorGUI.dict.get(i).getWord_explain());
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    /**
     * So sanh 2 xau theo thu tu bang chu cai
     * @param string1 xau 1
     * @param string2 xau 2
     * @return -1 neu xau 1 < xau 2; 0 neu xau 1 = xau 2; 1 neu xau 1 > xau 2
     */
    private static int compare2String(String string1, String string2) {
        int index = 0;
        while (index < string1.length() && index < string2.length()) {

            if (string1.charAt(index) > string2.charAt(index)) {
                return 1;
            }
            if (string1.charAt(index) < string2.charAt(index)) {
                return -1;
            }
            index++;
        }
        return Integer.compare(string1.length(), string2.length());
    }

    /**
     * Add a word to dict arraylist
     * @param wordTarget wordTarget
     * @param wordSpelling wordSpelling
     * @param wordDef wordDef
     * @return true if success, false if fail
     */
    public static boolean addWord(String wordTarget, String wordSpelling, String wordDef) {
        Word word = new Word(wordTarget,wordSpelling,wordDef);
        for (int i = 1; i < TranslatorGUI.dict.size()-1; i++) {
            if (compare2String(TranslatorGUI.dict.get(i).getWord_target(),wordTarget) == 0) {
                return false;
            }

            if (compare2String(TranslatorGUI.dict.get(i).getWord_target(),wordTarget) < 0 && compare2String(TranslatorGUI.dict.get(i+1).getWord_target(),wordTarget) > 0) {
                TranslatorGUI.dict.add(i+1,word);
                return true;
            }
        }
        TranslatorGUI.dict.add(word);
        return true;
    }

    public static void removeWord(int index) {
        TranslatorGUI.dict.remove(index);
    }

    public static boolean editWord(Word word) {
        for (int i = 0; i < TranslatorGUI.dict.size(); i++) {
            if (Objects.equals(TranslatorGUI.dict.get(i).getWord_target(), word.getWord_target())) {
                TranslatorGUI.dict.set(i,word);
                return true;
            }
        }
        return false;
    }

    public static int DictionaryGUILookup(String lookUpWord) {
        for (int i = 1; i < TranslatorGUI.dict.size(); i++) {
            if (TranslatorGUI.dict.get(i).getWord_target().equals(lookUpWord)) {
                return i;
            }
        }
        return 0;
    }



}
