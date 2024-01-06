package com.fiit.logic.events;

import com.fiit.logic.DownData;
import com.fiit.logic.ViewData;

import javax.crypto.ExemptionMechanismException;

public interface InputEventListener {
    void onEnter(int length);

    default void onDownEvent() throws ExemptionMechanismException {
        throw new ExemptionMechanismException();
    }

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent();

    ViewData onRightEvent();

    ViewData onRotateEvent();
}
