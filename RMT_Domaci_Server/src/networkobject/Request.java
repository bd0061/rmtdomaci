/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkobject;

import java.io.Serializable;

/**
 *
 * @author Djurkovic
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    public String command;
    public Object data;

    public Request(String command, Object data) {
        this.command = command;
        this.data = data;
    }

    public Request(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Request{" + "command=" + command + ", data=" + data + '}';
    }
    
    
    
    
}
