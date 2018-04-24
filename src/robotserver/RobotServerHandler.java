package robotserver;

import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import motorhat.AdafruitDcMotorHatControl;

public class RobotServerHandler extends ChannelInboundHandlerAdapter {

    char command;
    int length;
    Webcam webcam = Webcam.getDefault();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("Client Connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object in) throws Exception{
        AdafruitDcMotorHatControl control = new AdafruitDcMotorHatControl();
        String message = in.toString();
        command = message.charAt(0);
        length = message.length() - 1;
        int distance;
        int direction;
        switch (command){
            case 'G':  //GO
                distance = Integer.parseInt(message.substring(1, message.length()));
                System.out.println("Go: " + distance);
                if(distance > 0)
                    control.goForward(5);
                else if( distance < 0)
                    control.goBackwards(5);
                else
                    control.stop();
                break;
            case 'T':  //Turn
                direction = Integer.parseInt(message.substring(1, message.length()));
                System.out.println("Turn: " + direction);
                if(direction > 0)
                    control.turnRight(5);
                else if(direction < 0)
                    control.turnLeft(5);
                else
                    control.stop();
                break;
            case 'I':  //MINE
                //do mining stuff
                break;
            case 'D':  //DUMP
                //do
                break;
            case 'A':  //Auto
                //out.add(command);
                break;
            case 'M':  //Manual
                //out.add(command);
                break;
            case 'L':  //Location
                //do something
                break;
            case 'C':  //Camera
                System.out.println("File Name: " + in.toString());
                //String name = in.toString() + ".jpg";
                webcam.open();
                BufferedImage image = webcam.getImage();

                //BufferedImage img = ImageIO.read(Files.newInputStream(Paths.get(name)));
                ctx.writeAndFlush(image);
                break;
            case 'E':  //Emergency
                //out.add(command);
                control.stop();
                break;
            case 't':
                ctx.writeAndFlush("Connected Check!");
                break;
        }
    }
}
