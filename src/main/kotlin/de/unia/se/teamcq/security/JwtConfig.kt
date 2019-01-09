package de.unia.se.teamcq.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
data class JwtConfig(

    @Value("\${security.jwt.uri:/auth/**}")
    val Uri: String,

    @Value("\${security.jwt.header:Authorization}")
    val header: String,

    @Value("\${security.jwt.prefix:Bearer }")
    val prefix: String,

    @Value("\${security.jwt.expiration:#{24*60*60}}")
    val expiration: Int,

    @Value("\${security.jwt.public:" + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyFa0WF36CXzu6xU7tNk5" +
            "X5vwgERlUuWqt8Krut3lgjDEnc3MOB4AyU8ucun+z01avGF3xDN7eQpoGdmJScNA" +
            "c1tmGsTht5ozzTz1Xoa0sg9nlA3nVbN5BItV++1vdJgENwY6Lrwl7kvTqlAjGb8C" +
            "0lS8dgtctFABbV+bDEoRIfCdlek2lR/Z4JHWtojdGrjks0lTACV2cT54aWc5TyYp" +
            "8Xa7ToLKDRvnQQnUmAQyOjPJ2YOZL8r42Du6td6k5gKzF7QRpWfQN8ycvBdf56mp" +
            "3xUl1fMq2RrodBltWeC/Vs6hqv3bh8WZyu07wK8sEc1bT1lCCwdFHRKjPr5kdvFA" +
            "2QIDAQAB" + "}")
    val publicKey: String,

    @Value("\${security.jwt.private:" + "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDIVrRYXfoJfO7r" +
            "FTu02Tlfm/CARGVS5aq3wqu63eWCMMSdzcw4HgDJTy5y6f7PTVq8YXfEM3t5CmgZ" +
            "2YlJw0BzW2YaxOG3mjPNPPVehrSyD2eUDedVs3kEi1X77W90mAQ3BjouvCXuS9Oq" +
            "UCMZvwLSVLx2C1y0UAFtX5sMShEh8J2V6TaVH9ngkda2iN0auOSzSVMAJXZxPnhp" +
            "ZzlPJinxdrtOgsoNG+dBCdSYBDI6M8nZg5kvyvjYO7q13qTmArMXtBGlZ9A3zJy8" +
            "F1/nqanfFSXV8yrZGuh0GW1Z4L9WzqGq/duHxZnK7TvArywRzVtPWUILB0UdEqM+" +
            "vmR28UDZAgMBAAECggEAChAw5u9xi+B2r+BVK3Rt6VuP7SGCZXOb67cfoTdcO/nv" +
            "Jm4cAAWhzHrpvmP6pLciA6xxs61SWnYnXIUM3GOubRz+OAIDxJE+YOTa/nCNyxPz" +
            "BADeyfNF9PB61TjMDHNGYu0xJv/Ud+/fJgL4gGaT4bTX8qYHdhc+uxu+UhO2YJn6" +
            "xgHM0bFRIY22ea7eAY9lveR++Y7LxPi8Hb5/1Y476ibnJlwiksHIkKIcZbZH4AJc" +
            "0mHPL91MZ8uEX8h1hp/zMRbT0kSlxbDfC9qg1T3SVq3AsX1ahn+nzkmd9JRlvz1z" +
            "FwO/qDWliMWJId5twuvkZyT7QQjaLQWPfi8ssrDk8QKBgQDwKrlzHVTlaAn96IAd" +
            "+evrSTPXVynr2bu+i8C2BQq+NJpDqIVLqY68Zz9IWGb34EI+dnqj8N04okxxicGy" +
            "9SfSSUiwD30ZS8Axr3MMdmRQoyr3C936QF/1UghGpkrWhar/dGi2F+kio/EtzbYx" +
            "VxG0LywS0RznDu8DTu1iwKnjmwKBgQDVi82Pj5P9RKyMpI8lvmXB8iclVmosq7Ct" +
            "irSy4H93hyPoxmVQcEbr1EuOcI2NObsLfAaomHm0ndV0drO0u9xKy8YjmZzydpMM" +
            "bn6idgqXaBPpyyr2cL7QdGLwt8HmC64vFgMNfSKg9PoKQ0Jq6oHZR+JYIQXNRJg7" +
            "LJOBGbF2mwKBgGRo+G/wa3YeMU8+zZG606/jQxFdF+HLfnsD4uY25W7wfy6aXmBr" +
            "24lZgVO14hbwY8HJtyyjn1n2QQ+XEKzJYoTArustDphs20XqnKcxOG+eSHBRPdVK" +
            "5NOkIGGpVNCrwfpbqQu4o3ggIs2LnAi05mn27u5PD05rJ/KyJP2Mn3rRAoGAeO0K" +
            "ItmzfCB/dcVaKo1/Iiz2bfZZeaJgEmYRnZnzlMQ6WX6/sKVkA/fcMXUbM3YVrZM2" +
            "UhLGGLmoPqNZcGdRgCmUsmQytDrhWpDXtTgTF1ogrKwYUCXYaEMAJEBq1NKYeKGG" +
            "aQULrwoK7fTYj6LslRZX3oAUwQCvFBNSFDFwhTECgYA+0sDcApeSsb4uukg92Aci" +
            "fttz0VdocGT/o0B4Cg3TSk8XpDn+QE/cEq3wxaCPzbRF3YXOR0FDnujPp8G4GTKK" +
            "qMmJ0beUe9qSg0D0cEOLs6E63lC06ySjpJ0FQVs56uitUhqDAmEoh3Dv8WybP8/n" +
            "VNyvd2EDjpvGi+rzazlqow==" + "}")
    val privateKey: String
)
