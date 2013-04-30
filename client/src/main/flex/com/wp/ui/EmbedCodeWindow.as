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
import mx.events.CloseEvent;
import mx.managers.PopUpManager;

public class EmbedCodeWindow {
    private var titleWindow:TitleWindow;
    private var parent:DisplayObject;
    private var label:Text = new Text();
    private var callback:Function;

    public function EmbedCodeWindow(parent:DisplayObject) {
        this.parent = parent;

        titleWindow = new TitleWindow();
        titleWindow.title = "Embed Code:";
        titleWindow.showCloseButton = true;
        titleWindow.width = 240;
        titleWindow.height = 180;
        titleWindow.addEventListener(CloseEvent.CLOSE, onClose);
        titleWindow.addChild(label);
        var button:Button = new Button();
        button.label = "OK";
        button.addEventListener(MouseEvent.CLICK, onClose);
        titleWindow.addChild(button);
    }

    private function onClose():void {
        PopUpManager.removePopUp(titleWindow);
        callback();
    }

    public function showEmbedCode(embedCode:String, callback:Function):void {
        this.callback = callback;
        label.text = embedCode;
        PopUpManager.addPopUp(titleWindow, parent, true);
        PopUpManager.centerPopUp(titleWindow);
    }
}
}
