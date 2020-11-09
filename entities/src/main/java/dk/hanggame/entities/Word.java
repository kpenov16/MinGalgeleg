package dk.hanggame.entities;

public class Word {
    private String id = "";
    private String val = "";
    private Word(){}

    public static WordBuilder Builder(){
        return new WordBuilder();
    }

    public static class WordBuilder{
        private String id = "default id";
        private String val = "default val";
        private WordBuilder(){}

        public WordBuilder withId(String id){
            this.id = id;
            return this;
        }
        public WordBuilder withVal(String val){
            this.val = val;
            return this;
        }
        public Word build(){
            Word word = new Word();
            word.setId(id);
            word.setVal(val);
            return word;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String toString(){
        return String.format("Word: Id = %s, Val = %s", id, val);
    }
}
