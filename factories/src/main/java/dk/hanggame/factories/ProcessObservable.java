package dk.hanggame.factories;

public interface ProcessObservable{
    void addProcessObserver(ProcessObserver observer);
    void removeProcessObserver(ProcessObserver observer);
}
