package me.saro.ldap.jum.ldap.group

import javax.naming.directory.SearchResult


class Group {
    constructor(res: SearchResult) {
        val attrs = res.attributes
        this.gidNumber = attrs.get("gidNumber").get() as String
        this.objectClass = attrs.get("objectClass").get() as String
        this.cn = attrs.get("cn").get() as String
    }

    var gidNumber: String
    var objectClass: String
    var cn: String
}