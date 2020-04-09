package com.sskj.common;

import android.app.Application;


public class App {
    public static final Application INSTANCE;

    public App() {
    }

    static {
        Application app = null;

        try {
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke((Object) null);
            if (app == null) {
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        } catch (Exception var8) {
            System.out.println("Failed to get current application from AppGlobals." + var8.getMessage());

            try {
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke((Object) null);
            } catch (Exception var7) {
                System.out.println("Failed to get current application from ActivityThread." + var8.getMessage());
            }
        } finally {
            INSTANCE = app;
        }

    }
}

