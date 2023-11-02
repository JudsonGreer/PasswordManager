package assn07;

import javax.xml.transform.stream.StreamSource;
import java.net.StandardSocketOptions;
import java.util.*;

public class PasswordManager<K,V> implements Map<K,V> {
    private static final String MASTER_PASSWORD = "password123";
    private Account[] _passwords;

    private int _size;

    public PasswordManager() {
        _passwords = new Account[50];
    }


    // put
    @Override
    public void put(K key, V value) {

        int index = Math.abs(key.hashCode() % 50);
        if (_passwords[index] == null) {
            _passwords[index] = new Account(key, value);
            _size ++;
        } else { //store account at end of bucket
            Account cur = _passwords[index];
            Account head = _passwords[index];
            while (cur.getNext() != null) {
                if (cur.getWebsite().equals(key)) { //duplicate website, overwrite pwd
                    cur.setPassword(value);
                    return;
                }
                cur = cur.getNext();
            }
            if (cur.getWebsite().equals(key)) { //duplicate website, overwrite pwd
                cur.setPassword(value);
                return;
            }
            Account newAcc = new Account(key, value);
            newAcc.setNext(head);
            _passwords[index] = newAcc;
            _size++;
        }
    }

    // get
    @Override
    public V get(K key) {
        //System.out.println(key);
        int index = Math.abs(key.hashCode() % 50);
        Account cur = _passwords[index];
        while (cur != null) {
            //System.out.println(cur.getWebsite());
            if (cur.getWebsite().equals(key)) { //found account
                //System.out.println("this is password: " + cur.getPassword());
                return (V) cur.getPassword();
            }
            cur = cur.getNext();
        }
        //System.out.println("Password does not exist");
        return null;
    }

    // size
    @Override
    public int size() {
        return _size;
        //return size;
    }

    // keySet
    @Override
    public Set<K> keySet() {
        //iterate over the buckets
            //for each bucket, iterate the list and add websites to the return set
        //return the set
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < _passwords.length; i++) {
            if (_passwords[i] != null){
                Account cur = _passwords[i];
                while (cur != null) {
                    keySet.add((K) cur.getWebsite());
                    cur = cur.getNext();
                }
            }
        }
//        String[] strKeys = keySet.toArray(new String [keySet.size()]);
//        for (int i = 0; i < strKeys.length; i++) {
//            System.out.println(strKeys[i]);
//        }
        return keySet;
    }

    // remove
    @Override
    public V remove(K key) {
        //similar to get
        //before return, delete then return password
        //refer to LL assignment
        int index = Math.abs(key.hashCode() % 50);
        Account cur = _passwords[index];
        Account prev = null;
        if (cur == null) {
            return null;
            //account does not exist
        }
        if(cur.getWebsite().equals(key)) {
            _passwords[index] = cur.getNext();
            _size --;
            return (V) cur.getPassword();
        }
        while (cur != null) {
            if (cur.getWebsite().equals(key)) { //found account
                //TODO: remove account
                prev.setNext(cur.getNext());
                System.out.println("Account deleted");
                _size --;
                return (V) cur.getPassword();
            }
            prev = cur;
            cur = cur.getNext();
        }
        return null;
    }

    // checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
        //loop entire map (loop the entire array and each list in array)
        //for loop
            //while loop
        //for(int i =0; i < )
        List<K> dups = new ArrayList<>();
        for (int i = 0; i < _passwords.length; i++) {
            if (_passwords[i] != null){
                Account cur = _passwords[i];
                while (cur != null) {
                    if (cur.getPassword().equals(value)) {
                        dups.add((K) cur.getWebsite());
                    }
                    cur = cur.getNext();
                }
            }
        }

//        for (int i = 0; i < dups.size(); i++) {
//            System.out.println(dups.get(i));
//        }
        return dups;
    }

    // checkMasterPassword
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
        if (enteredPassword.equals(MASTER_PASSWORD)) {
            return true;
        }
        return false;
    }

    /*
    Generates random password of input length
     */
    @Override
    public String generateRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return generatedString;
    }

    //Used for testing
    public Account[] getPasswords() {
        return _passwords;
    }
}
