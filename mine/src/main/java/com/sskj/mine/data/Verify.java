package com.sskj.mine.data;

import com.sskj.common.App;
import com.sskj.mine.R;

public enum Verify {
    SMS(App.INSTANCE.getString(R.string.mine_verify1),
            App.INSTANCE.getString(R.string.mine_verify2)),
    GOOGLE(App.INSTANCE.getString(R.string.mine_verify3),
            App.INSTANCE.getString(R.string.mine_verify4)),
    EMAIL(App.INSTANCE.getString(R.string.mine_verify5),
            App.INSTANCE.getString(R.string.mine_verify6));

    String name;
    String type;

    Verify(String name,String type){
        this.name=name;
        this.type=type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
