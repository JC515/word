import java.util.Objects;

public class Word {
    public int uuid;
    public String data;
    public String explanation;
    public Word(int uuid, String data, String explanation) {
        this.uuid = uuid;
        this.data = data;
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Word{" +
                "uuid=" + uuid +
                ", data='" + data + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return uuid == word.uuid && Objects.equals(data, word.data) && Objects.equals(explanation, word.explanation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, data, explanation);
    }
}
