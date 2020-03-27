package net.thumbtack.school.file;

import com.google.gson.Gson;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileService {
    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return bytes;
        }
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            byteOut.write(array);
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray())) {
                byteOut.reset();
                for (int i = 0; i < array.length; i += 2) {
                    byteOut.write(byteIn.read());
                    byteIn.read();
                }
            }
            return byteOut.toByteArray();
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        try (BufferedOutputStream byfOut = new BufferedOutputStream(new FileOutputStream(file))) {
            byfOut.write(array);
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream byfIn = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[byfIn.available()];
            byfIn.read(bytes);
            return bytes;
        }
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(file))) {
            writeRect(dataOut, rect);
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException, ColorException {
        try (DataInputStream byIn = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(byIn.readInt(), byIn.readInt(), byIn.readInt(), byIn.readInt(), Color.RED);
        }
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle rectangle : rects) {
                writeRect(dataOut, rectangle);
            }
        }
    }

    private static void writeRect(DataOutputStream dataOut, Rectangle rectangle) throws IOException {
        dataOut.writeInt(rectangle.getTopLeft().getX());
        dataOut.writeInt(rectangle.getTopLeft().getY());
        dataOut.writeInt(rectangle.getBottomRight().getX());
        dataOut.writeInt(rectangle.getBottomRight().getY());
    }

    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException, ColorException {
        try (RandomAccessFile randFile = new RandomAccessFile(file, "r")) {
            int len = (int) randFile.length() / 16;
            Rectangle[] rectangles = new Rectangle[len];
            randFile.seek(randFile.length() + 16);
            for (int i = 0; i < len; i++) {
                randFile.seek(randFile.getFilePointer() - 32);
                rectangles[i] = new Rectangle(randFile.readInt(), randFile.readInt(), randFile.readInt(), randFile.readInt(), Color.RED);
            }
            return rectangles;
        }
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            String str = Integer.toString(rect.getTopLeft().getX()).concat(" ").concat(
                    Integer.toString(rect.getTopLeft().getY()).concat(" ").concat(
                            Integer.toString(rect.getBottomRight().getX()).concat(" ").concat(
                                    Integer.toString(rect.getBottomRight().getY()).concat(""))));
            fw.write(str);
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException, ColorException {
        try (FileReader fr = new FileReader(file);) {
            Scanner scan = new Scanner(fr);
            return new Rectangle(Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Color.RED);
        }
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(Integer.toString(rect.getTopLeft().getX()));
            fw.append("\n");
            fw.write(Integer.toString(rect.getTopLeft().getY()));
            fw.append("\n");
            fw.write(Integer.toString(rect.getBottomRight().getX()));
            fw.append("\n");
            fw.write(Integer.toString(rect.getBottomRight().getX()));
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws ColorException, IOException {
        try (FileReader fr = new FileReader(file);
             Scanner scan = new Scanner(fr)) {
            return new Rectangle(Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()),
                    Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()), Color.RED);
        }
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (FileWriter pw = new FileWriter(file, StandardCharsets.UTF_8)) {
            pw.append((trainee.getFullName().concat(" ".concat(Integer.toString(trainee.getRating())))));
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             Scanner scan = new Scanner(fr)) {
            return new Trainee(scan.next(), scan.next(), Integer.parseInt(scan.next()));
        }
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8)) {
            fw.write(trainee.getFirstName());
            fw.append("\n");
            fw.write(trainee.getLastName());
            fw.append("\n");
            fw.write(Integer.toString(trainee.getRating()));
        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws TrainingException, IOException {
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             Scanner scan = new Scanner(fr)) {
            return new Trainee(scan.nextLine(), scan.nextLine(), Integer.parseInt(scan.nextLine()));
        }
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(file))) {
            obOut.writeObject(trainee);
        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, TrainingException, ClassNotFoundException {
        try (ObjectInputStream obIn = new ObjectInputStream(new FileInputStream(file))) {
            return (Trainee) obIn.readObject();
        }
    }

    public static String serializeTraineeToJsonString(Trainee trainee) {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.append(serializeTraineeToJsonString(trainee));
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (FileReader fr = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(fr, Trainee.class);

        }
    }
}