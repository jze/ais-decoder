[![travis-ci](https://travis-ci.com/jze/ais-decoder.svg?branch=master)](https://travis-ci.com/jze/ais-decoder)

# AIS message decoder

This Java library can be used to decode AIS messages (e.g. `!AIVDM,1,1,,B,139O<l0P000dtRPO6lb@0?wb061l,0*35`)

It is just a selective fork of the FreeAIS codebase to include just the AIS message decoding part.

## Usage

In the simplest case, a message can be decoded with one line of Java code:

```java
IAISMessage message = new AISParser().parse("!AIVDM,1,1,,B,139O<l0P000dtRPO6lb@0?wb061l,0*35");
```

However, longer AIS messages will be split in two or more lines. In these cases the `parse` method will return `null` unless the last line of the message has been decoded.