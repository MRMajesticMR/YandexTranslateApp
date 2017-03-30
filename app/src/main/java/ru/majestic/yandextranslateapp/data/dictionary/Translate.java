package ru.majestic.yandextranslateapp.data.dictionary;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkadiy.zakharov on 28.03.2017.
 */

public class Translate {

    private String text;

    @SerializedName("pos")
    private String partOfSpeech;

    @SerializedName("syn")
    private List<Synonym> synonyms;

    @SerializedName("mean")
    private List<Meaning> meanings;

    @SerializedName("ex")
    private List<Example> examples;

    /**
     * Возвращает строку из всех синонимов
     *
     * @return
     */
    public String getAllSynonymsString() {
        StringBuilder stringBuilder = new StringBuilder(text);


        if (synonyms != null && !synonyms.isEmpty()) {
            for (Synonym synonym : synonyms) {
                stringBuilder.append(", ");
                stringBuilder.append(synonym.getText());
            }
        }

        return stringBuilder.toString();
    }

    public String getAllMeaningsString() {
        if (meanings != null && !meanings.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(meanings.get(0).getText());

            if (meanings.size() > 1) {
                for (int i = 1; i < meanings.size(); i++) {
                    stringBuilder.append(", ");
                    stringBuilder.append(meanings.get(i).getText());
                }
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public List<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Translate{");
        sb.append("text='").append(text).append('\'');
        sb.append(", partOfSpeech='").append(partOfSpeech).append('\'');
        sb.append(", synonyms=").append(synonyms);
        sb.append(", meanings=").append(meanings);
        sb.append(", examples=").append(examples);
        sb.append('}');
        return sb.toString();
    }
}
