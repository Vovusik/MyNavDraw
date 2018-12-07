package com.andrukhiv.mynavigationdrawer;

public class RegionColorUtils {

    public static int getMapColor(String id) {
        int mapColor = 0xFFFFFFFF;
        switch (id) {

            // CAT 3100-3400
            case "UKR283"://Crimea
                mapColor = 0xFFFC5722;
                break;
            case "UKR284"://Mykolayiv
                mapColor = 0xFFFC5722;
                break;
            case "UKR322"://Odessa
                mapColor = 0xFFFC5722;
                break;
            case "UKR331"://Zaporizhzhya
                mapColor = 0xFFFC5722;
                break;
            case "UKR4827"://Kherson
                mapColor = 0xFFFC5722;
                break;
            case "UKR5482"://Sevastopol
                mapColor = 0xFFFC5722;
                break;

            // CAT 2850-3000
            case "UKR293"://Transcarpathia
                mapColor = 0xFFFF9800;
                break;
                        case "UKR326"://Dnipropetrovs'k
                mapColor = 0xFFFF9800;
                break;
            case "UKR327"://Donets'k
                mapColor = 0xFFFF9800;
                break;
            case "UKR328"://Kharkiv
                mapColor = 0xFFFF9800;
                break;
            case "UKR329"://Luhans'k
                mapColor = 0xFFFF9800;
                break;
            case "UKR330"://Poltava
                mapColor = 0xFFFF9800;
                break;
            case "UKR320"://Кропивни́цкий
                mapColor = 0xFFFF9800;
                break;

            // CAT 2700-2800
            case "UKR288"://Chernivtsi
                mapColor = 0xFF4CAF50;
                break;
            case "UKR321"://Kiev
                mapColor = 0xFF4CAF50;
                break;
            case "UKR4826"://Kiev City
                mapColor = 0xFF4CAF50;
                break;
            case "UKR319"://Cherkasy
                mapColor = 0xFF4CAF50;
                break;
            case "UKR323"://Vinnytsya
                mapColor = 0xFF4CAF50;
                break;

            // CAT 2500-2600
            case "UKR285"://Chernihiv
                mapColor = 0xFF2196F3;
                break;
            case "UKR286"://Rivne
                mapColor = 0xFF2196F3;
                break;
            case "UKR289"://Ivano-Frankivs'k
                mapColor = 0xFF2196F3;
                break;
            case "UKR290"://Khmel'nyts'kyy
                mapColor = 0xFF2196F3;
                break;
            case "UKR291"://L'viv
                mapColor = 0xFF2196F3;
                break;
            case "UKR292"://Ternopil'
                mapColor = 0xFF2196F3;
                break;
            case "UKR318"://Volyn
                mapColor = 0xFF2196F3;
                break;
            case "UKR324"://Zhytomy
                mapColor = 0xFF2196F3;
                break;
            case "UKR325"://Sumy
                mapColor = 0xFF2196F3;
                break;
        }
        return mapColor;
    }
}
