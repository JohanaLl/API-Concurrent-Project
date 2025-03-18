module Photos {
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    exports com.dev.heavyphotos.model;
    exports com.dev.heavyphotos.services;
    exports com.dev.heavyphotos.client;
}