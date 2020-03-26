package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TraineeQueue {
    private Queue<Trainee> queue;

    public TraineeQueue() {
        queue = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee) {
        queue.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException {
        try {
            return queue.remove();
        } catch (NoSuchElementException e) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
    }

    public boolean isEmpty() {
        //REVU: есть метод isEmpty у очереди
        return queue.peek() == null;
    }
}
