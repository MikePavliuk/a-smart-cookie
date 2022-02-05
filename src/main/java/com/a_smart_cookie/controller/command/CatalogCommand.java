package com.a_smart_cookie.controller.command;

import javax.servlet.ServletException;
import java.io.IOException;

public final class CatalogCommand extends Command {

    @Override
    public void process() throws ServletException, IOException {
        forward("catalog");
    }

}
