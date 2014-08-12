/**
 * Created with JetBrains WebStorm.
 * User: small.guo
 * Date: 2/27/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
function GetURLParameter(url, sParam) {
    var para = url.split('?');
    var sURLVariables = para[1].split('&');
    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) {
            return sParameterName[1];
        }
    }
}