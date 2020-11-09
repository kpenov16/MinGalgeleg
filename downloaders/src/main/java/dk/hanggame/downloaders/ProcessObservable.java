package dk.hanggame.downloaders;

public interface ProcessObservable{
    void addProcessObserver(ProcessObserver observer);
    void removeProcessObserver(ProcessObserver observer);
}
