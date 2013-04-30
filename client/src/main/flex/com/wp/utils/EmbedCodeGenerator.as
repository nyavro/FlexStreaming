package com.wp.utils {
import mx.utils.StringUtil;

public class EmbedCodeGenerator {

    public static function generateCode(id:String, urlTemplate:String, embedWidth:int, embedHeight:int) {
        var url:String = StringUtil.substitute(urlTemplate, [id]);
        return StringUtil.substitute(
                "<div id='{0}'></div><script type='text/javascript'>jwplayer('{0}').setup({file: '{1}', width: '{2}',height: '{3}'});</script>",
                [id, url, embedWidth, embedHeight]
        );
    }
}
}
