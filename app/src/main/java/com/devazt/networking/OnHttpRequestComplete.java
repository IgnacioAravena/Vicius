package com.devazt.networking;

/**
 * Created by Neksze on 01/10/2015.
 */
public interface OnHttpRequestComplete {

        /**
         * Este método es ejecutado al terminar la ejecucion del método excecute del httpclient
         * devuelve un Response, con los datos de la solicitud
         * @param status Instancia de la clase Response
         */
        public void onComplete(Response status);

}
