package java_labs;

import java.util.HashMap;

import java.util.*;


 class Contacts {
    private HashMap<String, String> contacts;

    public Contacts() {
        this.contacts = new HashMap<>();
    }
// method 1
    public String show() {
        if (contacts.isEmpty()) {
            return "";
        }
        TreeSet<String> sortedNames = new TreeSet<>(contacts.keySet());
        StringBuilder result = new StringBuilder();
        for (String name : sortedNames) {
            result.append("[").append(name).append("](").append(contacts.get(name)).append(") ");
        }
        return result.toString().trim();
    }
// method 2
    public String find(String name) {
        if (name == null || !contacts.containsKey(name)) {
            return "error";
        }
        return "[" + contacts.get(name) + "]";
    }
// method 3
    public String add(String name, String phoneNumber) {
        if (name == null || phoneNumber == null || !isValidPhoneNumber(phoneNumber)) {
            return "error";
        }
        if (contacts.containsKey(name)) {
            return "error"; 
        }
        contacts.put(name, phoneNumber);
        return "";
    }
// method 4
    public String delete(String name) {
        if (name == null || !contacts.containsKey(name)) {
            return "error";
        }
        contacts.remove(name);
        return "";
    }

    private boolean isValidPhoneNumber(String phone) {

        if (!phone.matches("0\\d{2}-\\d{4}-\\d{4}")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        
    }
}
