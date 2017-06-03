package com.damian.salonapp.services.networking;

import com.damian.salonapp.services.sentto.SentTo;

/**
 * Created by damian on 3/6/17.
 */
/* Callback fired whenever a message is sent from ClientHandler class. Will have to create a reference and set it to activity*/
public interface AddToList {
    public void addToList(SentTo sentTo);
}
