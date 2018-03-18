package org.infoshare.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class UserStore {

    private static final String STORE = "users.store";
    private Logger LOG = LoggerFactory.getLogger(UserStore.class);

    public Map<Integer, User> getBase() {
        return base;
    }

    private Map<Integer, User> base;

    public UserStore() {
        read();
        if (null == base || base.isEmpty()) {
            init();
        }
    }

    public void init() {
        base = new HashMap<Integer, User>();
        User user1 = new User("Adam", "Iksinski", new Credentials("adam", "1234"));
        User user2 = new User("Karol", "Ygrekowski", null);
        add(user1);
        add(user2);
    }

    public void add(User user) {
        LOG.info("Adding to the store: " + user.toString());
        base.put(user.getId(), user);
        save();
    }

    public void remove(Integer userId) throws InvalidObjectException {
        if (!base.containsKey(userId)) {
            throw new InvalidObjectException("User [" + userId + "] not found");
        }
        base.remove(userId);
        save();
        return;
    }

    private void save(){
        try {
            FileOutputStream fos = new FileOutputStream(STORE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(base);
            oos.close();
        } catch (IOException e) {
            LOG.warn("Problem with saving userstore {}", e);
        }
    }

    private void read(){
        try {
            LOG.warn("Reading user.store file");
            FileInputStream fis = new FileInputStream(STORE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.base = (Map<Integer, User>) ois.readObject();
            ois.close();
        } catch (IOException|ClassNotFoundException e) {
            LOG.warn("Problem with saving userstore {}", e);
        }
    }
}
