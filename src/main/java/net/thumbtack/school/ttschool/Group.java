package net.thumbtack.school.ttschool;

import java.util.*;

public class Group {
    private String name;
    private String room;
    private List<Trainee> students;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        students = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) throws TrainingException {
        //с одним name.isEmpty() в условии не проходит тест testWrongName
        if (name == null || name.isEmpty()) {
            throw new TrainingException(net.thumbtack.school.ttschool.TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) throws TrainingException {
        //с одним name.isEmpty() в условии не проходит тест testWrongRoom
        if (room == null || room.isEmpty()) {
            throw new TrainingException(net.thumbtack.school.ttschool.TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    public List<Trainee> getTrainees() {
        return students;
    }

    public void addTrainee(Trainee trainee) {
        students.add(trainee);
    }


    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!students.remove(trainee)) {
            throw new TrainingException(net.thumbtack.school.ttschool.TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTrainee(int index) throws TrainingException {
        try {
            students.remove(index);
        } catch (IndexOutOfBoundsException ex) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        int i = 0;
        while (i < students.size()) {
            if (students.get(i).getFirstName().equals(firstName)) {
                return students.get(i);
            }
            i++;
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
        int i = 0;
        while (i < students.size()) {
            if (students.get(i).getFullName().equals(fullName)) {
                return students.get(i);
            }
            i++;
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant() {
        Collections.sort(students, Comparator.comparing(net.thumbtack.school.ttschool.Trainee::getFirstName));
    }

    public void sortTraineeListByRatingDescendant() {
        students.sort((o1, o2) -> {
            return -(o1.getRating() - o2.getRating());
        });
    }

    public void reverseTraineeList() {
        Collections.reverse(students);
    }

    public void rotateTraineeList(int positions) {
        Collections.rotate(students, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        int i = 1;
        if (students.size() == 0) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        sortTraineeListByRatingDescendant();
        while (students.get(0).getRating() == students.get(i).getRating()) {
            i++;
        }
        return students.subList(0, i);
    }

    public boolean hasDuplicates() {
        Set<Trainee> studentNotDuplicates = new HashSet<>(students);
        return studentNotDuplicates.size() != students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(room, group.room) &&
                Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, students);
    }

}
