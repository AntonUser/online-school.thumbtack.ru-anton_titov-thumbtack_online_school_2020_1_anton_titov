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
        FileOutputStream out = new FileOutputStream(new File(fileName));
        out.write(array);
        out.close();
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.write(array);
        out.close();
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        return readByteArrayFromBinaryFile(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream()) {
            byteOut.write(array);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            byteOut.reset();
            for (int i = 0; i < array.length; i += 2) {
                byteOut.write(byteIn.read());
                byteIn.read();
            }
            byteIn.close();
            return byteOut.toByteArray();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(new File(fileName), array);
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        BufferedOutputStream byfOut = new BufferedOutputStream(new FileOutputStream(file));
        byfOut.write(array);
        byfOut.close();
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        return readByteArrayFromBinaryFileBuffered(new File(fileName));
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        try (BufferedInputStream byfIn = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[byfIn.available()];
            byfIn.read(bytes);
            byfIn.close();
            return bytes;
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.writeInt(rect.getTopLeft().getX());
        randomAccessFile.writeInt(rect.getTopLeft().getY());
        randomAccessFile.writeInt(rect.getBottomRight().getX());
        randomAccessFile.writeInt(rect.getBottomRight().getY());
        randomAccessFile.close();
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException, ColorException {

        try (DataInputStream byIn = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(byIn.readInt(), byIn.readInt(), byIn.readInt(), byIn.readInt(), Color.RED);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        RandomAccessFile randFile = new RandomAccessFile(file, "rw");
        for (Rectangle rectangle : rects) {
            randFile.writeInt(rectangle.getTopLeft().getX());
            randFile.writeInt(rectangle.getTopLeft().getY());
            randFile.writeInt(rectangle.getBottomRight().getX());
            randFile.writeInt(rectangle.getBottomRight().getY());
        }
        randFile.close();
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
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            String str = Integer.toString(rect.getTopLeft().getX()).concat(" ").concat(
                    Integer.toString(rect.getTopLeft().getY()).concat(" ").concat(
                            Integer.toString(rect.getBottomRight().getX()).concat(" ").concat(
                                    Integer.toString(rect.getBottomRight().getY()).concat(""))));
            fw.write(str);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException, ColorException {
        try (FileReader fr = new FileReader(file);) {
            Scanner scan = new Scanner(fr);
            return new Rectangle(Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Integer.parseInt(scan.next()), Color.RED);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
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
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws ColorException, IOException {
        try (FileReader fr = new FileReader(file);
             Scanner scan = new Scanner(fr)) {
            return new Rectangle(Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()),
                    Integer.parseInt(scan.nextLine()), Integer.parseInt(scan.nextLine()), Color.RED);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        FileWriter pw = new FileWriter(file, StandardCharsets.UTF_8);
        pw.append((trainee.getFullName().concat(" ".concat(Integer.toString(trainee.getRating())))));
        pw.close();
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException, TrainingException {
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             Scanner scan = new Scanner(fr)) {
            return new Trainee(scan.next(), scan.next(), Integer.parseInt(scan.next()));
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
        fw.write(trainee.getFirstName());
        fw.append("\n");
        fw.write(trainee.getLastName());
        fw.append("\n");
        fw.write(Integer.toString(trainee.getRating()));
        fw.close();
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws TrainingException, IOException {
        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8);
             Scanner scan = new Scanner(fr)) {

            return new Trainee(scan.nextLine(), scan.nextLine(), Integer.parseInt(scan.nextLine()));
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        BufferedOutputStream buOut = new BufferedOutputStream(new FileOutputStream(file));
        buOut.write((trainee.getFullName().concat(" ".concat(Integer.toString(trainee.getRating())))).getBytes());
        buOut.close();
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, TrainingException {
        try (BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[(int) file.length()];
            bufInput.read(bytes);
            Scanner scanner = new Scanner(new String(bytes));
            return new Trainee(scanner.next(), scanner.next(), scanner.nextInt());
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
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
        FileWriter fw = new FileWriter(file);
        fw.append(serializeTraineeToJsonString(trainee));
        fw.close();
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (FileReader fr = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(fr, Trainee.class);

        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }
}