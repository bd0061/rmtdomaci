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
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    public boolean success;
    public String responseMessage;
    public Object responseData;

    public Response(boolean success, String responseMessage, Object responseData) {
        this.success = success;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }

    public Response(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Response(boolean success, String responseMessage) {
        this.success = success;
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "Response{" + "success=" + success + ", responseMessage=" + responseMessage + ", responseData=" + responseData + '}';
    }

}
