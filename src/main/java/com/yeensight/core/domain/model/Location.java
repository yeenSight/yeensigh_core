package com.yeensight.core.domain.model;

import java.net.URI;

public sealed interface Location {

    record Physical(String address) implements Location {
        public Physical {
            if (address == null || address.isBlank()) {
                throw new IllegalArgumentException("Address must not be blank");
            }
        }
    }

    record Online(URI url) implements Location {
        public Online {
            if (url == null || url.getScheme() == null
                    || !(url.getScheme().equals("http") || url.getScheme().equals("https"))) {
                throw new IllegalArgumentException("Online location must be an http(s) URL");
            }
        }
    }
}
