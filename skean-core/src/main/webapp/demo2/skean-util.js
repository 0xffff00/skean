class RestUrl {

    static url(origin, path) {
        let ru = new RestUrl();
        ru.origin = origin;
        ru.path = path;
        return ru;
    }

    pkMapper(pkMapper) {
        this.pkMapperpk = pkMapper;
        return this;
    }

    toListPath(params) {
        var url = new URL(this.origin + this.path);
        if (params) {
            Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        }
        return url.href;
    }
    toOnePath(item) {
        if (typeof (item) === 'string') {
            this.origin + this.path + item;
        }
        return this.origin + this.path + pkMapper(item);
    }

}
