package robotserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RobotServerDecoder extends ByteToMessageDecoder {

    int messageType;
    int length;
    int fileNameLength;
    char command;
    int direction;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(length == 0){
            getMessageType(in);
        }else{
            decodeData(in, out);
        }
    }

    private void decodeData(ByteBuf in, List<Object> out) {
        if(in.readableBytes() >= length){

            StringBuilder bld = new StringBuilder();
            for(int i = 0; i < length; i++){
                bld.append((char) in.readByte());
            }
            out.add(bld.toString());
            System.out.println(bld.toString());
            length = 0;
//            if(messageType == 0){
                //decode robot commands
//                for(int i = 0; i < length; i++){
//
//                }
//                command = in.readChar();
//                switch(command){
//                    case 'G':  //GO
//                        direction = in.readInt();
//                        //out.add(command);
//                        out.add(direction);
//                        break;
//                    case 'T':  //Turn
//                        direction = in.readInt();
//                        //out.add(command);
//                        out.add(direction);
//                        break;
//                    case 'I':  //MINE
//                        //out.add(command);
//                        break;
//                    case 'D':  //DUMP
//                        //out.add(command);
//                        break;
//                    case 'A':  //Auto
//                        //out.add(command);
//                        break;
//                    case 'M':  //Manual
//                        //out.add(command);
//                        break;
//                    case 'L':  //Location
//                        //out.add(command);
//                        break;
//                    case 'C':  //Camera
//                        fileNameLength = in.readShort();
//                        StringBuilder strBld = new StringBuilder();
//                        for(int i = 0; i < length; i++){
//                            strBld.append((char) in.readByte());
//                        }
//                        out.add(strBld.toString());
//                        break;
//                    case 'E':  //Emergency
//                        //out.add(command);
//                        break;
//                }


                //out.add(command);
//            }
//            if(messageType == 1){
//                //decode image name message
//                StringBuilder strBld = new StringBuilder();
//                for(int i = 0; i < length; i++){
//                    strBld.append((char) in.readByte());
//                }
//                out.add(strBld.toString());
//                length = 0;
//            }
        }
    }

    private void getMessageType(ByteBuf in) {
        if(in.readableBytes() >= 2){
            length = in.readShort();
        }
        System.out.println("Length: " + length);
    }
}
