package com.thetechnoobs.reactiontest;

import android.util.Log;

import java.util.Random;

public class RandomItems {
    public String[] buttons = {"B1x1","B1x2","B1x3","B2x1","B2x2","B2x3","B3x1","B3x2","B3x3","B4x1","B4x2","B4x3","B5x1","B5x2","B5x3","B6x1","B6x2","B6x3"};


    public String[] FillerItemsList = {"car", "hanger" , "fork" , "socks" , "card" , "coasters" , "outlet" , "buckle" , "USB" , "eraser" , "carrots" , "game" , "tweezers" , "clothes" , "headphones" , "lights" , "blanket" , "shovel" , "lamp" ,
            "cork" , "money" , "rubber duck" , "cup" , "food" , "wallet" , "pencil" , "bracelet" , "fridge" , "bag" , "conditioner" , "toe ring" , "checkbook" , "sandal" , "tape" , "sailboat" ,"apple" , "table" , "shade" ,
            "shirt" , "twister" , "brush" , "nail" , "card" , "peanuts" , "charger" , "frame" , "thermometer" , "note" , "keyboard" , "CD" , "lace" , "window" ,
            "clock" , "sponge" , "canvas" , "balloon" , "pillow" , "shoes" , "deodorant" , "soda can" , "stick" , "clippers" , "candle" , "wash" , "floor" , "tomato" , "broccoli" , "sidewalk" , "speakers" , "doll" , "album" ,
            "pad" , "soap" , "pen" , "wagon" ,"flowers" , "book" , "house" , "file" , "mop" , "desk" , "door", "clamp" , "button" , "drawer" , "slipper" , "truck" ,
            "television" , "magnet" , "sharpie" , "cap" , "thermostat" , "purse" , "mirror"};

    public String[] ItemsList = {"alarm", "billiards","boots","broom","camera","car","cat","circle","computer","computer_mouse","cursor","door","dress","gun","hat","hoe",
            "horse","house","jacket","keys","kitchen","bulb","liquor","moon","motorcycle","nail","noose","note","penis","phone","pig","pool","popsicle","postit","puppy",
            "rat","remote","rock","rope","rug","semi","shirt","shower","square","stairs", "sun","syringe","stoplight","truck"};




 public String randomFillerItem(){
     String FillerItem = "test";
     Random random = new Random();
     FillerItem = FillerItemsList[random.nextInt(FillerItemsList.length)];

     return FillerItem;
 }

 public String randomItem(){
     String realItem = "test";
     Random random = new Random();
     realItem = ItemsList[random.nextInt(ItemsList.length)];

     return realItem;
 }

 public String randomButton(){
     String rButton = "null";

     Random random = new Random();
     rButton = buttons[random.nextInt(buttons.length)];

     return rButton;
 }

 public String RandomIdGen(){
     String RandomID;

     Random random = new Random();
     RandomID =String.valueOf(random.nextInt(10000));

     return RandomID;
 }
}