.class final Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "LocalMovieBoxServer.kt"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/data/network/LocalMovieBoxServer;->handleClient(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lkotlin/coroutines/jvm/internal/SuspendLambda;",
        "Lkotlin/jvm/functions/Function2<",
        "Lkotlinx/coroutines/CoroutineScope;",
        "Lkotlin/coroutines/Continuation<",
        "-",
        "Lkotlin/Unit;",
        ">;",
        "Ljava/lang/Object;",
        ">;"
    }
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"
    }
    d2 = {
        "<anonymous>",
        "",
        "Lkotlinx/coroutines/CoroutineScope;"
    }
    k = 0x3
    mv = {
        0x2,
        0x2,
        0x0
    }
    xi = 0x30
.end annotation

.annotation runtime Lkotlin/coroutines/jvm/internal/DebugMetadata;
    c = "com.example.data.network.LocalMovieBoxServer$handleClient$2"
    f = "LocalMovieBoxServer.kt"
    i = {}
    l = {}
    m = "invokeSuspend"
    n = {}
    s = {}
.end annotation


# instance fields
.field final synthetic $socket:Ljava/net/Socket;

.field label:I

.field final synthetic this$0:Lcom/example/data/network/LocalMovieBoxServer;


# direct methods
.method constructor <init>(Ljava/net/Socket;Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/net/Socket;",
            "Lcom/example/data/network/LocalMovieBoxServer;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;",
            ">;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    const/4 p1, 0x2

    invoke-direct {p0, p1, p3}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

    return-void
.end method


# virtual methods
.method public final create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Object;",
            "Lkotlin/coroutines/Continuation<",
            "*>;)",
            "Lkotlin/coroutines/Continuation<",
            "Lkotlin/Unit;",
            ">;"
        }
    .end annotation

    new-instance p1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;

    iget-object v0, p0, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    invoke-direct {p1, v0, p0, p2}, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;-><init>(Ljava/net/Socket;Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V

    check-cast p1, Lkotlin/coroutines/Continuation;

    return-object p1
.end method

.method public bridge synthetic invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .registers 3

    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    check-cast p2, Lkotlin/coroutines/Continuation;

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->invoke(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public final invoke(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lkotlinx/coroutines/CoroutineScope;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lkotlin/Unit;",
            ">;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    move-result-object p0

    check-cast p0, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;

    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    invoke-virtual {p0, p1}, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .registers 21

    move-object/from16 v1, p0

    const-string v0, " "

    const-string v2, "LocalMovieBoxServer"

    invoke-static {}, Lkotlin/coroutines/intrinsics/IntrinsicsKt;->getCOROUTINE_SUSPENDED()Ljava/lang/Object;

    .line 82
    iget v3, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->label:I

    if-nez v3, :cond_174

    invoke-static/range {p1 .. p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 84
    :try_start_10
    new-instance v3, Ljava/io/BufferedReader;

    new-instance v4, Ljava/io/InputStreamReader;

    iget-object v5, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v5}, Ljava/net/Socket;->getInputStream()Ljava/io/InputStream;

    move-result-object v5

    invoke-direct {v4, v5}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;)V

    check-cast v4, Ljava/io/Reader;

    invoke-direct {v3, v4}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 85
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v4

    if-nez v4, :cond_30

    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_2a
    .catch Ljava/lang/Exception; {:try_start_10 .. :try_end_2a} :catch_153
    .catchall {:try_start_10 .. :try_end_2a} :catchall_151

    .line 135
    :try_start_2a
    iget-object v1, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->close()V
    :try_end_2f
    .catch Ljava/lang/Exception; {:try_start_2a .. :try_end_2f} :catch_2f

    :catch_2f
    return-object v0

    .line 86
    :cond_30
    :try_start_30
    check-cast v4, Ljava/lang/CharSequence;

    const/4 v10, 0x1

    new-array v5, v10, [Ljava/lang/String;

    const/4 v11, 0x0

    aput-object v0, v5, v11

    const/4 v8, 0x6

    const/4 v9, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x0

    invoke-static/range {v4 .. v9}, Lkotlin/text/StringsKt;->split$default(Ljava/lang/CharSequence;[Ljava/lang/String;ZIILjava/lang/Object;)Ljava/util/List;

    move-result-object v4

    .line 87
    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v5

    const/4 v6, 0x2

    if-ge v5, v6, :cond_4f

    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;
    :try_end_49
    .catch Ljava/lang/Exception; {:try_start_30 .. :try_end_49} :catch_153
    .catchall {:try_start_30 .. :try_end_49} :catchall_151

    .line 135
    :try_start_49
    iget-object v1, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->close()V
    :try_end_4e
    .catch Ljava/lang/Exception; {:try_start_49 .. :try_end_4e} :catch_4e

    :catch_4e
    return-object v0

    .line 88
    :cond_4f
    :try_start_4f
    invoke-interface {v4, v11}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    .line 89
    invoke-interface {v4, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    .line 93
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v7
    :try_end_5f
    .catch Ljava/lang/Exception; {:try_start_4f .. :try_end_5f} :catch_153
    .catchall {:try_start_4f .. :try_end_5f} :catchall_151

    move v8, v11

    .line 94
    :goto_60
    const-string v9, "substring(...)"

    if-eqz v7, :cond_9a

    :try_start_64
    move-object v12, v7

    check-cast v12, Ljava/lang/CharSequence;

    invoke-interface {v12}, Ljava/lang/CharSequence;->length()I

    move-result v12

    if-lez v12, :cond_9a

    .line 95
    const-string v12, "Content-Length:"

    invoke-static {v7, v12, v10}, Lkotlin/text/StringsKt;->startsWith(Ljava/lang/String;Ljava/lang/String;Z)Z

    move-result v12

    if-eqz v12, :cond_95

    const/16 v8, 0xf

    .line 96
    invoke-virtual {v7, v8}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7, v9}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    check-cast v7, Ljava/lang/CharSequence;

    invoke-static {v7}, Lkotlin/text/StringsKt;->trim(Ljava/lang/CharSequence;)Ljava/lang/CharSequence;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Lkotlin/text/StringsKt;->toIntOrNull(Ljava/lang/String;)Ljava/lang/Integer;

    move-result-object v7

    if-eqz v7, :cond_94

    invoke-virtual {v7}, Ljava/lang/Integer;->intValue()I

    move-result v7

    move v8, v7

    goto :goto_95

    :cond_94
    move v8, v11

    .line 98
    :cond_95
    :goto_95
    invoke-virtual {v3}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v7
    :try_end_99
    .catch Ljava/lang/Exception; {:try_start_64 .. :try_end_99} :catch_153
    .catchall {:try_start_64 .. :try_end_99} :catchall_151

    goto :goto_60

    .line 102
    :cond_9a
    const-string v7, ""

    const/4 v12, -0x1

    if-lez v8, :cond_b4

    .line 103
    :try_start_9f
    new-array v13, v8, [C

    move v14, v11

    :goto_a2
    if-ge v14, v8, :cond_ae

    sub-int v15, v8, v14

    .line 106
    invoke-virtual {v3, v13, v14, v15}, Ljava/io/BufferedReader;->read([CII)I

    move-result v15

    if-eq v15, v12, :cond_ae

    add-int/2addr v14, v15

    goto :goto_a2

    .line 108
    :cond_ae
    new-instance v3, Ljava/lang/String;

    .line 110
    invoke-direct {v3, v13, v11, v14}, Ljava/lang/String;-><init>([CII)V

    goto :goto_b5

    :cond_b4
    move-object v3, v7

    .line 115
    :goto_b5
    move-object v13, v4

    check-cast v13, Ljava/lang/CharSequence;

    const/16 v17, 0x6

    const/16 v18, 0x0

    const/16 v14, 0x3f

    const/4 v15, 0x0

    const/16 v16, 0x0

    invoke-static/range {v13 .. v18}, Lkotlin/text/StringsKt;->indexOf$default(Ljava/lang/CharSequence;CIZILjava/lang/Object;)I

    move-result v8

    if-eq v8, v12, :cond_cf

    .line 116
    invoke-virtual {v4, v11, v8}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v13

    invoke-static {v13, v9}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    goto :goto_d0

    :cond_cf
    move-object v13, v4

    :goto_d0
    if-eq v8, v12, :cond_da

    add-int/2addr v8, v10

    .line 117
    invoke-virtual {v4, v8}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7, v9}, Lkotlin/jvm/internal/Intrinsics;->checkNotNullExpressionValue(Ljava/lang/Object;Ljava/lang/String;)V

    .line 119
    :cond_da
    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Incoming: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 120
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    # invokes: Lcom/example/data/network/LocalMovieBoxServer;->routeRequest(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lkotlin/Pair;
    invoke-static {v0, v5, v13, v7, v3}, Lcom/example/data/network/LocalMovieBoxServer;->access$routeRequest(Lcom/example/data/network/LocalMovieBoxServer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lkotlin/Pair;

    move-result-object v0

    .line 122
    const-string v3, "/docs"

    invoke-static {v13, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_132

    const-string v3, "/docs/"

    invoke-static {v13, v3}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_10f

    goto :goto_132

    .line 123
    :cond_10f
    const-string v3, ".jpg"

    const/4 v4, 0x0

    invoke-static {v13, v3, v11, v6, v4}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_12f

    const-string v3, ".jpeg"

    invoke-static {v13, v3, v11, v6, v4}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_121

    goto :goto_12f

    .line 124
    :cond_121
    const-string v3, ".png"

    invoke-static {v13, v3, v11, v6, v4}, Lkotlin/text/StringsKt;->endsWith$default(Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_12c

    const-string v3, "image/png"

    goto :goto_134

    .line 125
    :cond_12c
    const-string v3, "application/json; charset=utf-8"

    goto :goto_134

    .line 123
    :cond_12f
    :goto_12f
    const-string v3, "image/jpeg"

    goto :goto_134

    .line 122
    :cond_132
    :goto_132
    const-string v3, "text/html; charset=utf-8"

    .line 127
    :goto_134
    iget-object v4, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    iget-object v5, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v0}, Lkotlin/Pair;->getFirst()Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/Number;

    invoke-virtual {v6}, Ljava/lang/Number;->intValue()I

    move-result v6

    invoke-virtual {v0}, Lkotlin/Pair;->getSecond()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    # invokes: Lcom/example/data/network/LocalMovieBoxServer;->sendResponse(Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V
    invoke-static {v4, v5, v6, v0, v3}, Lcom/example/data/network/LocalMovieBoxServer;->access$sendResponse(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;)V
    :try_end_14b
    .catch Ljava/lang/Exception; {:try_start_9f .. :try_end_14b} :catch_153
    .catchall {:try_start_9f .. :try_end_14b} :catchall_151

    .line 135
    :catch_14b
    :goto_14b
    :try_start_14b
    iget-object v0, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v0}, Ljava/net/Socket;->close()V
    :try_end_150
    .catch Ljava/lang/Exception; {:try_start_14b .. :try_end_150} :catch_16b

    goto :goto_16b

    :catchall_151
    move-exception v0

    goto :goto_16e

    :catch_153
    move-exception v0

    .line 129
    :try_start_154
    const-string v3, "Error handling client"

    check-cast v0, Ljava/lang/Throwable;

    invoke-static {v2, v3, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    :try_end_15b
    .catchall {:try_start_154 .. :try_end_15b} :catchall_151

    .line 131
    :try_start_15b
    iget-object v4, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    iget-object v5, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    const-string v7, "{\"code\": 500, \"msg\": \"Internal Server Error\"}"

    const/16 v9, 0x8

    const/4 v10, 0x0

    const/16 v6, 0x1f4

    const/4 v8, 0x0

    invoke-static/range {v4 .. v10}, Lcom/example/data/network/LocalMovieBoxServer;->sendResponse$default(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;ILjava/lang/String;Ljava/lang/String;ILjava/lang/Object;)V
    :try_end_16a
    .catch Ljava/lang/Exception; {:try_start_15b .. :try_end_16a} :catch_14b
    .catchall {:try_start_15b .. :try_end_16a} :catchall_151

    goto :goto_14b

    .line 138
    :catch_16b
    :goto_16b
    sget-object v0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    return-object v0

    .line 135
    :goto_16e
    :try_start_16e
    iget-object v1, v1, Lcom/example/data/network/LocalMovieBoxServer$handleClient$2;->$socket:Ljava/net/Socket;

    invoke-virtual {v1}, Ljava/net/Socket;->close()V
    :try_end_173
    .catch Ljava/lang/Exception; {:try_start_16e .. :try_end_173} :catch_173

    .line 136
    :catch_173
    throw v0

    .line 82
    :cond_174
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v1, "call to \'resume\' before \'invoke\' with coroutine"

    invoke-direct {v0, v1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method
