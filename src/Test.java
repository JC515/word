import java.io.*;

class A {
    public static void main(String[] args) throws IOException {
        File f = new File("D:\\java_project\\LexicalUniverse\\src\\serialized_object\\need_up.ser");
        NeedUp a = new NeedUp();
        a.lastUUID = 1;
        ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(f));
        o.writeObject(a);
        System.out.println("yes");
    }
}

class B {
    public static void main(String[] args) {
        File f = new File("D:\\java_project\\LexicalUniverse\\src\\serialized_object\\need_up.ser");
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
            NeedUp n = (NeedUp) inputStream.readObject();
            System.out.println(n.lastUUID);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}