package br.uff.caronet.controller;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Car;
import br.uff.caronet.model.User;

public class UserController {

    private Dao dao = Dao.get();

    public void logOut(){
        dao.logOut();
    }

    public void registerUser(User user){
        dao.setUser(user);
        dao.getClUsers().document(user.getId()).set(user);
    }

    public Car getUserCar (){
        return dao.getUser().getCar();
    }

    public User getUser() {
        return dao.getUser();
    }
}
