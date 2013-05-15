package com.wp.utils {
import mx.utils.StringUtil;

public class EmbedCodeGenerator {

    public static function generateCode(id:String, url:String, embedWidth:int, embedHeight:int) {
        return StringUtil.substitute(
                "<div id=\"{0}\" onclick=\"initPlayer()\">" +
                "    <a>Show comment</a>" +
                "</div>" +
                "<script type=\'text/javascript\'>" +
                "function initPlayer() { " +
                    "jwplayer(\"{0}\").setup({" +
                        "\"flashplayer\": \"http://www.longtailvideo.com/content/ova/jwplayer/player.swf\"," +
                        "\"playlist\": [" +
                            "{" +
                                "\"file\":  \"{1}/video?id={0}\"," +
                                "\"image\": \"{1}/thumbnail?id={0}\"" +
                            "}" +
                        "]," +
                        "\"width\": {2}," +
                        "\"height\": {3}" +
                    "});" +
                "}" +
                "</script>",
                [id, url, embedWidth, embedHeight]
        );
    }
}
}
