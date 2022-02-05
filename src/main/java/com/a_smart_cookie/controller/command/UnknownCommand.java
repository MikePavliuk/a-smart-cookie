package com.a_smart_cookie.controller.command;

import javax.servlet.ServletException;
import java.io.IOException;

public final class UnknownCommand extends Command {

    @Override
    public void process() throws IOException {
        sendRedirect("error");
    }
}
