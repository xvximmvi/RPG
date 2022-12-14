package at.ac.fhcampuswien.tile;


import at.ac.fhcampuswien.main.MyPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Manager {
    MyPanel panel;
    Tile[] tile;
    int[][] mapTilesNum;

    public Manager(MyPanel panel){
        this.panel = panel;
        tile = new Tile[10];    //kinds of tiles

        mapTilesNum = new int [panel.maxScreenCol][panel.maxScreenRow]; //Map Size as Screen Size

        tileImage();            //call tileImage()
        loadMap();              //call loadMap()
    }

    public void tileImage(){        //same as Player
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResource("/Tiles/Boden.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResource("/Tiles/WandSchlafzimmer.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResource("/Tiles/Outlines.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/Maps/Bedroom.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < panel.maxScreenCol && row < panel.maxScreenRow){
                String line = bufferedReader.readLine();

                while(col < panel.maxScreenCol){
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTilesNum[col][row] = num;
                    col++;
                }
                if(col == panel.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col< panel.maxScreenCol && row < panel.maxScreenRow)  //instead of writing every tile
        {
            int tileNum = mapTilesNum[col][row];
            graphics2D.drawImage(tile[tileNum].image, x, y, panel.tileSize, panel.tileSize, null); //draw
            col++;                  //draw next tile
            x += panel.tileSize;    //next block

            if(col == panel.maxScreenCol){  //if col finished go to next row and repeat
                col = 0;
                x = 0;
                row++;
                y += panel.tileSize;
            }
        }
    }
}
