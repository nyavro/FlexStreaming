/**
 * Created with IntelliJ IDEA.
 * User: eny
 * Date: 4/30/13
 * Time: 10:11 AM
 * To change this template use File | Settings | File Templates.
 */
package com.wp.ui {
import flash.display.DisplayObject;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.sampler.getSavedThis;

import mx.containers.TitleWindow;
import mx.controls.Alert;
import mx.controls.Button;
import mx.controls.Label;
import mx.controls.Text;
import mx.controls.TextArea;
import mx.events.CloseEvent;
import mx.managers.PopUpManager;

public class EmbedCodeWindow {
    private var titleWindow:TitleWindow;
    private var parent:DisplayObject;
    private var label:TextArea = new TextArea();
    private var callback:Function;

    public function EmbedCodeWindow(parent:DisplayObject) {
        this.parent = parent;

        titleWindow = new TitleWindow();
        titleWindow.title = "Embed Code:";
        titleWindow.showCloseButton = true;
        titleWindow.width = 240;
        titleWindow.height = 200;
        label.width = 240;
        titleWindow.addEventListener(CloseEvent.CLOSE, onClose);
        titleWindow.addChild(label);
        var button:Button = new Button();
        button.label = "OK";
        button.addEventListener(MouseEvent.CLICK, okClose);
        titleWindow.addChild(button);
    }

    private function close():void {
        PopUpManager.removePopUp(titleWindow);
        callback();
    }

    private function okClose(event:MouseEvent):void {
        close();
    }

    private function onClose(event:CloseEvent):void {
        close();
    }

    public function showEmbedCode(embedCode:String, callback:Function):void {
        this.callback = callback;
        label.text = embedCode;
        PopUpManager.addPopUp(titleWindow, parent, true);
        PopUpManager.centerPopUp(titleWindow);
    }
}
}
