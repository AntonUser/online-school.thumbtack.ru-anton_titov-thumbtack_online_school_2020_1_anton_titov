package net.thumbtack.school.ttschool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TraineeMap {

    private Map<Trainee, String> dependency;

    public TraineeMap() {
        dependency = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (dependency.put(trainee, institute) != null) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (dependency.replace(trainee, institute) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        if (dependency.remove(trainee) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public int getTraineesCount() {
        return dependency.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        String nameInstitute = dependency.get(trainee);
        if (nameInstitute == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return nameInstitute;
    }

    public Set<Trainee> getAllTrainees() {
        return dependency.keySet();
    }

    public Set<String> getAllInstitutes() {
        return new HashSet<>(dependency.values());
    }

    public boolean isAnyFromInstitute(String institute) {
        return dependency.containsValue(institute);
    }

}
