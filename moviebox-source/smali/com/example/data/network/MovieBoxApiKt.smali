.class public final Lcom/example/data/network/MovieBoxApiKt;
.super Ljava/lang/Object;
.source "MovieBoxApi.kt"


# annotations
.annotation system Ldalvik/annotation/SourceDebugExtension;
    value = "SMAP\nMovieBoxApi.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovieBoxApi.kt\ncom/example/data/network/MovieBoxApiKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,644:1\n1869#2,2:645\n1869#2,2:647\n1563#2:649\n1634#2,2:650\n1636#2:653\n1#3:652\n*S KotlinDebug\n*F\n+ 1 MovieBoxApi.kt\ncom/example/data/network/MovieBoxApiKt\n*L\n390#1:645,2\n444#1:647,2\n466#1:649\n466#1:650,2\n466#1:653\n*E\n"
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\u0008\u0002\n\u0002\u0010\u0012\n\u0002\u0008\u000b\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u001a\u000e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0008\u001a\u00020\u0001\u001a\u000e\u0010\t\u001a\u00020\u00012\u0006\u0010\u0008\u001a\u00020\u0001\u001a\u000e\u0010\n\u001a\u00020\u00012\u0006\u0010\u0008\u001a\u00020\u0001\u001a\u000e\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0008\u001a\u00020\u0001\u001a\u001e\u0010\u000c\u001a\u00020\u00012\u0006\u0010\u0008\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001\u00a8\u0006\u000f"
    }
    d2 = {
        "md5",
        "",
        "input",
        "hmacMd5",
        "",
        "keyBytes",
        "dataBytes",
        "flattenCategoryJson",
        "jsonStr",
        "rewriteTrendingJson",
        "rewriteStreamJson",
        "rewriteSearchJson",
        "rewriteDetailJson",
        "subjectId",
        "mockHost",
        "app"
    }
    k = 0x2
    mv = {
        0x2,
        0x2,
        0x0
    }
    xi = 0x30
.end annotation


# direct methods
.method public static final flattenCategoryJson(Ljava/lang/String;)Ljava/lang/String;
    .registers 10

    const-string v0, "jsonStr"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 382
    new-instance v0, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {v0}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {v0}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object v0

    .line 383
    const-class v1, Ljava/util/Map;

    invoke-virtual {v0, v1}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object v0

    .line 384
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    instance-of v2, v1, Ljava/util/Map;

    const/4 v3, 0x0

    if-eqz v2, :cond_20

    check-cast v1, Ljava/util/Map;

    goto :goto_21

    :cond_20
    move-object v1, v3

    :goto_21
    if-nez v1, :cond_24

    goto :goto_45

    .line 386
    :cond_24
    const-string v2, "data"

    invoke-interface {v1, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v4, v1, Ljava/util/Map;

    if-eqz v4, :cond_31

    check-cast v1, Ljava/util/Map;

    goto :goto_32

    :cond_31
    move-object v1, v3

    :goto_32
    if-nez v1, :cond_35

    goto :goto_45

    .line 387
    :cond_35
    const-string v4, "list"

    invoke-interface {v1, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v5, v1, Ljava/util/List;

    if-eqz v5, :cond_42

    check-cast v1, Ljava/util/List;

    goto :goto_43

    :cond_42
    move-object v1, v3

    :goto_43
    if-nez v1, :cond_46

    :goto_45
    return-object p0

    .line 389
    :cond_46
    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    check-cast p0, Ljava/util/List;

    .line 390
    check-cast v1, Ljava/lang/Iterable;

    .line 645
    invoke-interface {v1}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v1

    :cond_53
    :goto_53
    invoke-interface {v1}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    const-string v6, "items"

    if-eqz v5, :cond_75

    invoke-interface {v1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/util/Map;

    .line 391
    invoke-interface {v5, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v5

    instance-of v6, v5, Ljava/util/List;

    if-eqz v6, :cond_6c

    check-cast v5, Ljava/util/List;

    goto :goto_6d

    :cond_6c
    move-object v5, v3

    :goto_6d
    if-eqz v5, :cond_53

    .line 393
    check-cast v5, Ljava/util/Collection;

    invoke-interface {p0, v5}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    goto :goto_53

    :cond_75
    const/4 v1, 0x3

    .line 398
    new-array v1, v1, [Lkotlin/Pair;

    const/16 v3, 0xc8

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    const-string v5, "code"

    invoke-static {v5, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/4 v5, 0x0

    aput-object v3, v1, v5

    .line 399
    const-string v3, "message"

    const-string v7, "success"

    invoke-static {v3, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    const/4 v7, 0x1

    aput-object v3, v1, v7

    const/4 v3, 0x2

    .line 401
    new-array v8, v3, [Lkotlin/Pair;

    invoke-static {v4, p0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v8, v5

    .line 402
    invoke-static {v6, p0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object p0

    aput-object p0, v8, v7

    .line 400
    invoke-static {v8}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object p0

    invoke-static {v2, p0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object p0

    aput-object p0, v1, v3

    .line 397
    invoke-static {v1}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object p0

    .line 405
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "toJson(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0
.end method

.method public static final hmacMd5([B[B)[B
    .registers 5

    const-string v0, "keyBytes"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    const-string v0, "dataBytes"

    invoke-static {p1, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 124
    const-string v0, "HmacMD5"

    invoke-static {v0}, Ljavax/crypto/Mac;->getInstance(Ljava/lang/String;)Ljavax/crypto/Mac;

    move-result-object v1

    .line 125
    new-instance v2, Ljavax/crypto/spec/SecretKeySpec;

    invoke-direct {v2, p0, v0}, Ljavax/crypto/spec/SecretKeySpec;-><init>([BLjava/lang/String;)V

    .line 126
    check-cast v2, Ljava/security/Key;

    invoke-virtual {v1, v2}, Ljavax/crypto/Mac;->init(Ljava/security/Key;)V

    .line 127
    invoke-virtual {v1, p1}, Ljavax/crypto/Mac;->doFinal([B)[B

    move-result-object p0

    const-string p1, "doFinal(...)"

    invoke-static {p0, p1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0
.end method

.method public static final md5(Ljava/lang/String;)Ljava/lang/String;
    .registers 12

    const-string v0, "input"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 118
    const-string v0, "MD5"

    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 119
    sget-object v1, Lkotlin/text/Charsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-virtual {p0, v1}, Ljava/lang/String;->getBytes(Ljava/nio/charset/Charset;)[B

    move-result-object p0

    const-string v1, "getBytes(...)"

    invoke-static {p0, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    invoke-virtual {v0, p0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v2

    .line 120
    invoke-static {v2}, Lkotlin/jvm/internal/Intrinsics;->checkNotNull(Ljava/lang/Object;)V

    const-string p0, ""

    move-object v3, p0

    check-cast v3, Ljava/lang/CharSequence;

    new-instance v8, Lcom/example/data/network/MovieBoxApiKt$$ExternalSyntheticLambda0;

    invoke-direct {v8}, Lcom/example/data/network/MovieBoxApiKt$$ExternalSyntheticLambda0;-><init>()V

    const/16 v9, 0x1e

    const/4 v10, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    invoke-static/range {v2 .. v10}, Lkotlin/collections/ArraysKt;->joinToString$default([BLjava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Lkotlin/jvm/functions/Function1;ILjava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static final md5$lambda$0(B)Ljava/lang/CharSequence;
    .registers 2

    .line 120
    sget-object v0, Lkotlin/jvm/internal/StringCompanionObject;->INSTANCE:Lkotlin/jvm/internal/StringCompanionObject;

    invoke-static {p0}, Ljava/lang/Byte;->valueOf(B)Ljava/lang/Byte;

    move-result-object p0

    filled-new-array {p0}, [Ljava/lang/Object;

    move-result-object p0

    const/4 v0, 0x1

    invoke-static {p0, v0}, Ljava/util/Arrays;->copyOf([Ljava/lang/Object;I)[Ljava/lang/Object;

    move-result-object p0

    const-string v0, "%02x"

    invoke-static {v0, p0}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "format(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast p0, Ljava/lang/CharSequence;

    return-object p0
.end method

.method public static final rewriteDetailJson(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 23

    move-object/from16 v0, p0

    move-object/from16 v1, p1

    move-object/from16 v2, p2

    const-string v3, "stillPath"

    const-string v4, "duration"

    const-string v5, "title"

    const-string v6, "jsonStr"

    invoke-static {v0, v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    const-string v6, "subjectId"

    invoke-static {v1, v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    const-string v6, "mockHost"

    invoke-static {v2, v6}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 498
    new-instance v6, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {v6}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {v6}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object v6

    .line 499
    const-class v7, Ljava/util/Map;

    invoke-virtual {v6, v7}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object v6

    .line 500
    invoke-virtual {v6, v0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v7

    instance-of v8, v7, Ljava/util/Map;

    if-eqz v8, :cond_35

    check-cast v7, Ljava/util/Map;

    goto :goto_36

    :cond_35
    const/4 v7, 0x0

    :goto_36
    if-nez v7, :cond_39

    return-object v0

    .line 501
    :cond_39
    invoke-static {v7}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v7

    const/16 v0, 0xc8

    .line 504
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    const-string v8, "code"

    invoke-interface {v7, v8, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 505
    const-string v0, "message"

    const-string v8, "success"

    invoke-interface {v7, v0, v8}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 507
    const-string v8, "data"

    invoke-interface {v7, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v10, v0, Ljava/util/Map;

    if-eqz v10, :cond_5c

    check-cast v0, Ljava/util/Map;

    goto :goto_5d

    :cond_5c
    const/4 v0, 0x0

    :goto_5d
    const-string v10, "toJson(...)"

    if-nez v0, :cond_69

    invoke-virtual {v6, v7}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    invoke-static {v0, v10}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object v0

    .line 508
    :cond_69
    invoke-static {v0}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object v11

    .line 511
    const-string v0, "languages"

    invoke-interface {v11, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    instance-of v12, v0, Ljava/util/List;

    if-eqz v12, :cond_7a

    check-cast v0, Ljava/util/List;

    goto :goto_7b

    :cond_7a
    const/4 v0, 0x0

    .line 512
    :goto_7b
    const-string v12, "type"

    if-eqz v0, :cond_109

    .line 513
    new-instance v16, Ljava/util/ArrayList;

    invoke-direct/range {v16 .. v16}, Ljava/util/ArrayList;-><init>()V

    move-object/from16 v9, v16

    check-cast v9, Ljava/util/List;

    .line 514
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_8c
    :goto_8c
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v16

    if-eqz v16, :cond_f6

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v16

    const/16 p0, 0x0

    move-object/from16 v13, v16

    check-cast v13, Ljava/util/Map;

    const/16 v16, 0x1

    .line 515
    invoke-interface {v13, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    instance-of v14, v15, Ljava/lang/String;

    if-eqz v14, :cond_a9

    check-cast v15, Ljava/lang/String;

    goto :goto_aa

    :cond_a9
    const/4 v15, 0x0

    .line 516
    :goto_aa
    const-string v14, "resource"

    invoke-static {v15, v14}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_8c

    .line 517
    const-string v14, "id"

    invoke-interface {v13, v14}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v14

    instance-of v15, v14, Ljava/lang/String;

    if-eqz v15, :cond_bf

    check-cast v14, Ljava/lang/String;

    goto :goto_c0

    :cond_bf
    const/4 v14, 0x0

    .line 518
    :goto_c0
    const-string v15, "name"

    invoke-interface {v13, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v13

    move-object/from16 v18, v0

    instance-of v0, v13, Ljava/lang/String;

    if-eqz v0, :cond_cf

    check-cast v13, Ljava/lang/String;

    goto :goto_d0

    :cond_cf
    const/4 v13, 0x0

    :goto_d0
    if-eqz v14, :cond_f3

    move-object/from16 v19, v13

    const/4 v0, 0x2

    .line 521
    new-array v13, v0, [Lkotlin/Pair;

    const-string v0, "resourceId"

    invoke-static {v0, v14}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v13, p0

    if-nez v19, :cond_e4

    .line 522
    const-string v0, "Resource"

    goto :goto_e6

    :cond_e4
    move-object/from16 v0, v19

    :goto_e6
    invoke-static {v15, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v13, v16

    .line 520
    invoke-static {v13}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-interface {v9, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_f3
    move-object/from16 v0, v18

    goto :goto_8c

    :cond_f6
    const/16 p0, 0x0

    const/16 v16, 0x1

    .line 527
    move-object v0, v9

    check-cast v0, Ljava/util/Collection;

    invoke-interface {v0}, Ljava/util/Collection;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_10d

    .line 528
    const-string v0, "resourceDetectors"

    invoke-interface {v11, v0, v9}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_10d

    :cond_109
    const/16 p0, 0x0

    const/16 v16, 0x1

    .line 533
    :cond_10d
    :goto_10d
    const-string v0, "subjectType"

    invoke-interface {v11, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_119

    invoke-interface {v11, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    :cond_119
    if-eqz v0, :cond_2fa

    .line 534
    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_2fa

    invoke-static {v0}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v0

    if-eqz v0, :cond_2fa

    invoke-virtual {v0}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v12

    double-to-int v0, v12

    const/4 v9, 0x2

    if-ne v0, v9, :cond_2fa

    .line 539
    :try_start_12f
    new-instance v0, Lokhttp3/OkHttpClient$Builder;

    invoke-direct {v0}, Lokhttp3/OkHttpClient$Builder;-><init>()V

    .line 540
    sget-object v9, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    const-wide/16 v12, 0x5

    invoke-virtual {v0, v12, v13, v9}, Lokhttp3/OkHttpClient$Builder;->connectTimeout(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;

    move-result-object v0

    .line 541
    sget-object v9, Ljava/util/concurrent/TimeUnit;->SECONDS:Ljava/util/concurrent/TimeUnit;

    invoke-virtual {v0, v12, v13, v9}, Lokhttp3/OkHttpClient$Builder;->readTimeout(JLjava/util/concurrent/TimeUnit;)Lokhttp3/OkHttpClient$Builder;

    move-result-object v0

    .line 542
    invoke-virtual {v0}, Lokhttp3/OkHttpClient$Builder;->build()Lokhttp3/OkHttpClient;

    move-result-object v0

    .line 545
    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v9, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v9, "/episodes/"

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 546
    new-instance v2, Lokhttp3/Request$Builder;

    invoke-direct {v2}, Lokhttp3/Request$Builder;-><init>()V

    .line 547
    invoke-virtual {v2, v1}, Lokhttp3/Request$Builder;->url(Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 548
    const-string v2, "User-Agent"

    const-string v9, "MovieBoxPro/16.2.1 (Android 12; Pixel 6)"

    invoke-virtual {v1, v2, v9}, Lokhttp3/Request$Builder;->header(Ljava/lang/String;Ljava/lang/String;)Lokhttp3/Request$Builder;

    move-result-object v1

    .line 549
    invoke-virtual {v1}, Lokhttp3/Request$Builder;->build()Lokhttp3/Request;

    move-result-object v1

    .line 551
    invoke-virtual {v0, v1}, Lokhttp3/OkHttpClient;->newCall(Lokhttp3/Request;)Lokhttp3/Call;

    move-result-object v0

    invoke-interface {v0}, Lokhttp3/Call;->execute()Lokhttp3/Response;

    move-result-object v0

    .line 552
    invoke-virtual {v0}, Lokhttp3/Response;->isSuccessful()Z

    move-result v1

    if-eqz v1, :cond_2fa

    .line 553
    invoke-virtual {v0}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v0

    if-eqz v0, :cond_18b

    invoke-virtual {v0}, Lokhttp3/ResponseBody;->string()Ljava/lang/String;

    move-result-object v0

    goto :goto_18c

    :cond_18b
    const/4 v0, 0x0

    .line 554
    :goto_18c
    move-object v1, v0

    check-cast v1, Ljava/lang/CharSequence;

    if-eqz v1, :cond_2fa

    invoke-interface {v1}, Ljava/lang/CharSequence;->length()I

    move-result v1

    if-nez v1, :cond_199

    goto/16 :goto_2fa

    .line 555
    :cond_199
    invoke-virtual {v6, v0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    instance-of v1, v0, Ljava/util/Map;

    if-eqz v1, :cond_1a4

    check-cast v0, Ljava/util/Map;

    goto :goto_1a5

    :cond_1a4
    const/4 v0, 0x0

    :goto_1a5
    if-eqz v0, :cond_1ac

    .line 556
    invoke-interface {v0, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto :goto_1ad

    :cond_1ac
    const/4 v0, 0x0

    :goto_1ad
    instance-of v1, v0, Ljava/util/Map;

    if-eqz v1, :cond_1b4

    check-cast v0, Ljava/util/Map;

    goto :goto_1b5

    :cond_1b4
    const/4 v0, 0x0

    :goto_1b5
    if-eqz v0, :cond_1be

    .line 557
    const-string v1, "seasons"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto :goto_1bf

    :cond_1be
    const/4 v0, 0x0

    :goto_1bf
    instance-of v1, v0, Ljava/util/List;

    if-eqz v1, :cond_1c6

    check-cast v0, Ljava/util/List;

    goto :goto_1c7

    :cond_1c6
    const/4 v0, 0x0

    :goto_1c7
    if-eqz v0, :cond_2fa

    .line 559
    new-instance v1, Ljava/util/ArrayList;

    invoke-direct {v1}, Ljava/util/ArrayList;-><init>()V

    check-cast v1, Ljava/util/List;

    .line 560
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_1d4
    :goto_1d4
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_2f0

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    .line 561
    const-string v9, "seasonNumber"

    invoke-interface {v2, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9
    :try_end_1e6
    .catch Ljava/lang/Exception; {:try_start_12f .. :try_end_1e6} :catch_2fd

    const-string v12, "season"

    if-nez v9, :cond_1fc

    :try_start_1ea
    invoke-interface {v2, v12}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_1fc

    const-string v9, "se"

    invoke-interface {v2, v9}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    if-nez v9, :cond_1fc

    invoke-static/range {v16 .. v16}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    .line 562
    :cond_1fc
    invoke-virtual {v9}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v9

    if-eqz v9, :cond_20c

    invoke-virtual {v9}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v13

    double-to-int v9, v13

    goto :goto_20e

    :cond_20c
    move/from16 v9, v16

    .line 564
    :goto_20e
    const-string v13, "episodes"

    invoke-interface {v2, v13}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v13, v2, Ljava/util/List;

    if-eqz v13, :cond_21b

    check-cast v2, Ljava/util/List;

    goto :goto_21c

    :cond_21b
    const/4 v2, 0x0

    :goto_21c
    if-eqz v2, :cond_1d4

    .line 566
    new-instance v13, Ljava/util/ArrayList;

    invoke-direct {v13}, Ljava/util/ArrayList;-><init>()V

    check-cast v13, Ljava/util/List;

    .line 567
    invoke-interface {v2}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_229
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v14

    if-eqz v14, :cond_2c8

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v14

    check-cast v14, Ljava/util/Map;

    .line 568
    const-string v15, "episodeNumber"

    invoke-interface {v14, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15
    :try_end_23b
    .catch Ljava/lang/Exception; {:try_start_1ea .. :try_end_23b} :catch_2fd

    move-object/from16 p1, v0

    const-string v0, "episode"

    if-nez v15, :cond_253

    :try_start_241
    invoke-interface {v14, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    if-nez v15, :cond_253

    const-string v15, "ep"

    invoke-interface {v14, v15}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v15

    if-nez v15, :cond_253

    invoke-static/range {v16 .. v16}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v15

    .line 569
    :cond_253
    invoke-virtual {v15}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v15

    invoke-static {v15}, Lkotlin/text/StringsKt;->toDoubleOrNull(Ljava/lang/String;)Ljava/lang/Double;

    move-result-object v15
    :try_end_25b
    .catch Ljava/lang/Exception; {:try_start_241 .. :try_end_25b} :catch_2fd

    if-eqz v15, :cond_267

    move/from16 p2, v9

    move-object/from16 v18, v10

    :try_start_261
    invoke-virtual {v15}, Ljava/lang/Double;->doubleValue()D

    move-result-wide v9

    double-to-int v9, v9

    goto :goto_26d

    :cond_267
    move/from16 p2, v9

    move-object/from16 v18, v10

    move/from16 v9, v16

    .line 570
    :goto_26d
    invoke-interface {v14, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v10

    if-nez v10, :cond_286

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v15, "Episode "

    invoke-virtual {v10, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v9}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    :cond_286
    const/4 v15, 0x4

    .line 573
    new-array v15, v15, [Lkotlin/Pair;

    invoke-static {v9}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    invoke-static {v0, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v15, p0

    .line 574
    invoke-static {v5, v10}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    aput-object v0, v15, v16

    .line 575
    invoke-interface {v14, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0
    :try_end_29d
    .catch Ljava/lang/Exception; {:try_start_261 .. :try_end_29d} :catch_2f8

    const-string v9, ""

    if-nez v0, :cond_2a2

    move-object v0, v9

    :cond_2a2
    :try_start_2a2
    invoke-static {v4, v0}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/16 v17, 0x2

    aput-object v0, v15, v17

    .line 576
    invoke-interface {v14, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_2b1

    goto :goto_2b2

    :cond_2b1
    move-object v9, v0

    :goto_2b2
    invoke-static {v3, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v0

    const/4 v9, 0x3

    aput-object v0, v15, v9

    .line 572
    invoke-static {v15}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    invoke-interface {v13, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    move-object/from16 v0, p1

    move/from16 v9, p2

    move-object/from16 v10, v18

    goto/16 :goto_229

    :cond_2c8
    move-object/from16 p1, v0

    move/from16 p2, v9

    move-object/from16 v18, v10

    const/4 v0, 0x2

    .line 580
    new-array v2, v0, [Lkotlin/Pair;

    invoke-static/range {p2 .. p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v9

    invoke-static {v12, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v9

    aput-object v9, v2, p0

    .line 581
    const-string v9, "episodeList"

    invoke-static {v9, v13}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v9

    aput-object v9, v2, v16

    .line 579
    invoke-static {v2}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v2

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    move-object/from16 v0, p1

    move-object/from16 v10, v18

    goto/16 :goto_1d4

    :cond_2f0
    move-object/from16 v18, v10

    .line 585
    const-string v0, "seasonList"

    invoke-interface {v11, v0, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_2f7
    .catch Ljava/lang/Exception; {:try_start_2a2 .. :try_end_2f7} :catch_2f8

    goto :goto_303

    :catch_2f8
    move-exception v0

    goto :goto_300

    :cond_2fa
    :goto_2fa
    move-object/from16 v18, v10

    goto :goto_303

    :catch_2fd
    move-exception v0

    move-object/from16 v18, v10

    .line 590
    :goto_300
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 594
    :goto_303
    invoke-interface {v7, v8, v11}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 595
    invoke-virtual {v6, v7}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    move-object/from16 v1, v18

    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object v0
.end method

.method public static final rewriteSearchJson(Ljava/lang/String;)Ljava/lang/String;
    .registers 4

    const-string v0, "jsonStr"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 485
    new-instance v0, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {v0}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {v0}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object v0

    .line 486
    const-class v1, Ljava/util/Map;

    invoke-virtual {v0, v1}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object v0

    .line 487
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    instance-of v2, v1, Ljava/util/Map;

    if-eqz v2, :cond_1f

    check-cast v1, Ljava/util/Map;

    goto :goto_20

    :cond_1f
    const/4 v1, 0x0

    :goto_20
    if-nez v1, :cond_23

    return-object p0

    .line 488
    :cond_23
    invoke-static {v1}, Lkotlin/collections/MapsKt;->toMutableMap(Ljava/util/Map;)Ljava/util/Map;

    move-result-object p0

    const/16 v1, 0xc8

    .line 491
    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    const-string v2, "code"

    invoke-interface {p0, v2, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 492
    const-string v1, "message"

    const-string v2, "success"

    invoke-interface {p0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 494
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "toJson(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0
.end method

.method public static final rewriteStreamJson(Ljava/lang/String;)Ljava/lang/String;
    .registers 23

    move-object/from16 v0, p0

    const-string v1, "jsonStr"

    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 427
    new-instance v1, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {v1}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {v1}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object v1

    .line 428
    const-class v2, Ljava/util/Map;

    invoke-virtual {v1, v2}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object v1

    .line 429
    invoke-virtual {v1, v0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/Map;

    if-eqz v3, :cond_21

    check-cast v2, Ljava/util/Map;

    goto :goto_22

    :cond_21
    const/4 v2, 0x0

    :goto_22
    if-nez v2, :cond_25

    return-object v0

    .line 431
    :cond_25
    const-string v0, "url"

    invoke-interface {v2, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    instance-of v5, v3, Ljava/lang/String;

    if-eqz v5, :cond_32

    check-cast v3, Ljava/lang/String;

    goto :goto_33

    :cond_32
    const/4 v3, 0x0

    :goto_33
    const-string v5, ""

    if-nez v3, :cond_38

    move-object v3, v5

    .line 432
    :cond_38
    const-string v6, "cookie"

    invoke-interface {v2, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    instance-of v8, v7, Ljava/lang/String;

    if-eqz v8, :cond_45

    check-cast v7, Ljava/lang/String;

    goto :goto_46

    :cond_45
    const/4 v7, 0x0

    :goto_46
    if-nez v7, :cond_49

    move-object v7, v5

    .line 433
    :cond_49
    const-string v8, "resolutions"

    invoke-interface {v2, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v8

    instance-of v9, v8, Ljava/lang/String;

    if-eqz v9, :cond_56

    check-cast v8, Ljava/lang/String;

    goto :goto_57

    :cond_56
    const/4 v8, 0x0

    :goto_57
    if-nez v8, :cond_5a

    move-object v8, v5

    .line 435
    :cond_5a
    new-instance v9, Ljava/util/ArrayList;

    invoke-direct {v9}, Ljava/util/ArrayList;-><init>()V

    check-cast v9, Ljava/util/List;

    .line 436
    move-object v10, v3

    check-cast v10, Ljava/lang/CharSequence;

    invoke-interface {v10}, Ljava/lang/CharSequence;->length()I

    move-result v10

    const/4 v11, 0x3

    const/4 v14, 0x1

    if-lez v10, :cond_13f

    .line 437
    move-object v15, v8

    check-cast v15, Ljava/lang/CharSequence;

    invoke-interface {v15}, Ljava/lang/CharSequence;->length()I

    move-result v8

    const-string v10, "size"

    const-string v4, "quality"

    const/16 p0, 0x0

    const/4 v13, 0x4

    const/16 v21, 0x2

    const-string v12, "Auto"

    if-lez v8, :cond_11b

    .line 439
    new-array v8, v13, [Lkotlin/Pair;

    invoke-static {v4, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v16

    aput-object v16, v8, p0

    .line 440
    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v16

    aput-object v16, v8, v14

    .line 441
    invoke-static {v10, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v8, v21

    .line 442
    invoke-static {v6, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v8, v11

    .line 438
    invoke-static {v8}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v8

    invoke-interface {v9, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 444
    new-array v8, v14, [Ljava/lang/String;

    const-string v12, ","

    aput-object v12, v8, p0

    const/16 v19, 0x6

    const/16 v20, 0x0

    const/16 v17, 0x0

    const/16 v18, 0x0

    move-object/from16 v16, v8

    invoke-static/range {v15 .. v20}, Lkotlin/text/StringsKt;->split$default(Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;

    move-result-object v8

    check-cast v8, Ljava/lang/Iterable;

    .line 647
    invoke-interface {v8}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v8

    :goto_bb
    invoke-interface {v8}, Ljava/util/Iterator;->hasNext()Z

    move-result v12

    if-eqz v12, :cond_118

    invoke-interface {v8}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v12

    check-cast v12, Ljava/lang/String;

    .line 445
    check-cast v12, Ljava/lang/CharSequence;

    invoke-static {v12}, Lkotlin/text/StringsKt;->trim(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v12

    .line 446
    move-object v15, v12

    check-cast v15, Ljava/lang/CharSequence;

    invoke-interface {v15}, Ljava/lang/CharSequence;->length()I

    move-result v15

    if-lez v15, :cond_113

    .line 448
    new-array v15, v13, [Lkotlin/Pair;

    move/from16 v16, v14

    new-instance v14, Ljava/lang/StringBuilder;

    invoke-direct {v14}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v14, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    const-string v14, "p"

    invoke-virtual {v12, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v12

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v4, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v15, p0

    .line 449
    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v15, v16

    .line 450
    const-string v12, "Dynamic"

    invoke-static {v10, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v15, v21

    .line 451
    invoke-static {v6, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v12

    aput-object v12, v15, v11

    .line 447
    invoke-static {v15}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v12

    invoke-interface {v9, v12}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_115

    :cond_113
    move/from16 v16, v14

    :goto_115
    move/from16 v14, v16

    goto :goto_bb

    :cond_118
    move/from16 v16, v14

    goto :goto_145

    :cond_11b
    move/from16 v16, v14

    .line 457
    new-array v8, v13, [Lkotlin/Pair;

    invoke-static {v4, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v8, p0

    .line 458
    invoke-static {v0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v8, v16

    .line 459
    invoke-static {v10, v12}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v8, v21

    .line 460
    invoke-static {v6, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v8, v11

    .line 456
    invoke-static {v8}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    invoke-interface {v9, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_145

    :cond_13f
    move/from16 v16, v14

    const/16 p0, 0x0

    const/16 v21, 0x2

    .line 465
    :goto_145
    const-string v3, "subtitles"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    instance-of v3, v2, Ljava/util/List;

    if-eqz v3, :cond_152

    check-cast v2, Ljava/util/List;

    goto :goto_153

    :cond_152
    const/4 v2, 0x0

    :goto_153
    if-nez v2, :cond_159

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v2

    .line 466
    :cond_159
    check-cast v2, Ljava/lang/Iterable;

    .line 649
    new-instance v3, Ljava/util/ArrayList;

    const/16 v4, 0xa

    invoke-static {v2, v4}, Lkotlin/collections/CollectionsKt;->collectionSizeOrDefault(Ljava/lang/Iterable;I)I

    move-result v4

    invoke-direct {v3, v4}, Ljava/util/ArrayList;-><init>(I)V

    check-cast v3, Ljava/util/Collection;

    .line 650
    invoke-interface {v2}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_16c
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v4

    if-eqz v4, :cond_1f0

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v4

    .line 651
    check-cast v4, Ljava/util/Map;

    .line 467
    const-string v6, "lan"

    invoke-interface {v4, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    instance-of v7, v6, Ljava/lang/String;

    if-eqz v7, :cond_185

    check-cast v6, Ljava/lang/String;

    goto :goto_186

    :cond_185
    const/4 v6, 0x0

    :goto_186
    if-nez v6, :cond_189

    move-object v6, v5

    .line 468
    :cond_189
    const-string v7, "lanName"

    invoke-interface {v4, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    instance-of v8, v7, Ljava/lang/String;

    if-eqz v8, :cond_196

    check-cast v7, Ljava/lang/String;

    goto :goto_197

    :cond_196
    const/4 v7, 0x0

    :goto_197
    if-nez v7, :cond_19a

    move-object v7, v5

    .line 469
    :cond_19a
    invoke-interface {v4, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    instance-of v8, v4, Ljava/lang/String;

    if-eqz v8, :cond_1a5

    check-cast v4, Ljava/lang/String;

    goto :goto_1a6

    :cond_1a5
    const/4 v4, 0x0

    :goto_1a6
    if-nez v4, :cond_1a9

    move-object v4, v5

    .line 471
    :cond_1a9
    new-array v8, v11, [Lkotlin/Pair;

    check-cast v7, Ljava/lang/CharSequence;

    invoke-interface {v7}, Ljava/lang/CharSequence;->length()I

    move-result v10

    if-nez v10, :cond_1c0

    check-cast v6, Ljava/lang/CharSequence;

    invoke-interface {v6}, Ljava/lang/CharSequence;->length()I

    move-result v7

    if-nez v7, :cond_1bd

    const-string v6, "English"

    :cond_1bd
    move-object v7, v6

    check-cast v7, Ljava/lang/String;

    :cond_1c0
    const-string v6, "language"

    invoke-static {v6, v7}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    aput-object v6, v8, p0

    .line 472
    invoke-static {v0, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v6

    aput-object v6, v8, v16

    .line 473
    const-string v6, ".vtt"

    move/from16 v7, v16

    invoke-static {v4, v6, v7}, Lkotlin/text/StringsKt;->endsWith(Ljava/lang/String;Ljava/lang/String;Z)Z

    move-result v4

    if-eqz v4, :cond_1db

    const-string v4, "vtt"

    goto :goto_1dd

    :cond_1db
    const-string v4, "srt"

    :goto_1dd
    const-string v6, "format"

    invoke-static {v6, v4}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v4

    aput-object v4, v8, v21

    .line 470
    invoke-static {v8}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v4

    .line 651
    invoke-interface {v3, v4}, Ljava/util/Collection;->add(Ljava/lang/Object;)Z

    const/16 v16, 0x1

    goto/16 :goto_16c

    .line 653
    :cond_1f0
    check-cast v3, Ljava/util/List;

    move/from16 v0, v21

    .line 478
    new-array v0, v0, [Lkotlin/Pair;

    const-string v2, "streamList"

    invoke-static {v2, v9}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    aput-object v2, v0, p0

    .line 479
    const-string v2, "subTitleList"

    invoke-static {v2, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/16 v16, 0x1

    aput-object v2, v0, v16

    .line 477
    invoke-static {v0}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v0

    .line 481
    invoke-virtual {v1, v0}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "toJson(...)"

    invoke-static {v0, v1}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object v0
.end method

.method public static final rewriteTrendingJson(Ljava/lang/String;)Ljava/lang/String;
    .registers 9

    const-string v0, "jsonStr"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullParameter(Ljava/lang/Object;Ljava/lang/String;)V

    .line 409
    new-instance v0, Lcom/squareup/moshi/Moshi$Builder;

    invoke-direct {v0}, Lcom/squareup/moshi/Moshi$Builder;-><init>()V

    invoke-virtual {v0}, Lcom/squareup/moshi/Moshi$Builder;->build()Lcom/squareup/moshi/Moshi;

    move-result-object v0

    .line 410
    const-class v1, Ljava/util/Map;

    invoke-virtual {v0, v1}, Lcom/squareup/moshi/Moshi;->adapter(Ljava/lang/Class;)Lcom/squareup/moshi/JsonAdapter;

    move-result-object v0

    .line 411
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->fromJson(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    instance-of v2, v1, Ljava/util/Map;

    const/4 v3, 0x0

    if-eqz v2, :cond_20

    check-cast v1, Ljava/util/Map;

    goto :goto_21

    :cond_20
    move-object v1, v3

    :goto_21
    if-nez v1, :cond_24

    return-object p0

    .line 413
    :cond_24
    const-string p0, "data"

    invoke-interface {v1, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    instance-of v2, v1, Ljava/util/List;

    if-eqz v2, :cond_31

    move-object v3, v1

    check-cast v3, Ljava/util/List;

    :cond_31
    if-nez v3, :cond_37

    invoke-static {}, Lkotlin/collections/CollectionsKt;->emptyList()Ljava/util/List;

    move-result-object v3

    :cond_37
    const/4 v1, 0x3

    .line 416
    new-array v1, v1, [Lkotlin/Pair;

    const/16 v2, 0xc8

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const-string v4, "code"

    invoke-static {v4, v2}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/4 v4, 0x0

    aput-object v2, v1, v4

    .line 417
    const-string v2, "message"

    const-string v5, "success"

    invoke-static {v2, v5}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v2

    const/4 v5, 0x1

    aput-object v2, v1, v5

    const/4 v2, 0x2

    .line 419
    new-array v6, v2, [Lkotlin/Pair;

    const-string v7, "list"

    invoke-static {v7, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v7

    aput-object v7, v6, v4

    .line 420
    const-string v4, "items"

    invoke-static {v4, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object v3

    aput-object v3, v6, v5

    .line 418
    invoke-static {v6}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object v3

    invoke-static {p0, v3}, Lkotlin/TuplesKt;->to(Ljava/lang/Object;Ljava/lang/Object;)Lkotlin/Pair;

    move-result-object p0

    aput-object p0, v1, v2

    .line 415
    invoke-static {v1}, Lkotlin/collections/MapsKt;->mapOf([Lkotlin/Pair;)Ljava/util/Map;

    move-result-object p0

    .line 423
    invoke-virtual {v0, p0}, Lcom/squareup/moshi/JsonAdapter;->toJson(Ljava/lang/Object;)Ljava/lang/String;

    move-result-object p0

    const-string v0, "toJson(...)"

    invoke-static {p0, v0}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    return-object p0
.end method
