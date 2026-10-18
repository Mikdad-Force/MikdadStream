.class final Lcom/example/data/network/LocalMovieBoxServer$start$1;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "LocalMovieBoxServer.kt"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/data/network/LocalMovieBoxServer;->start()V
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
    c = "com.example.data.network.LocalMovieBoxServer$start$1"
    f = "LocalMovieBoxServer.kt"
    i = {}
    l = {}
    m = "invokeSuspend"
    n = {}
    s = {}
.end annotation


# instance fields
.field private synthetic L$0:Ljava/lang/Object;

.field label:I

.field final synthetic this$0:Lcom/example/data/network/LocalMovieBoxServer;


# direct methods
.method constructor <init>(Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/network/LocalMovieBoxServer;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/network/LocalMovieBoxServer$start$1;",
            ">;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    const/4 p1, 0x2

    invoke-direct {p0, p1, p2}, Lkotlin/coroutines/jvm/internal/SuspendLambda;-><init>(ILkotlin/coroutines/Continuation;)V

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

    new-instance v0, Lcom/example/data/network/LocalMovieBoxServer$start$1;

    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    invoke-direct {v0, p0, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1;-><init>(Lcom/example/data/network/LocalMovieBoxServer;Lkotlin/coroutines/Continuation;)V

    iput-object p1, v0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->L$0:Ljava/lang/Object;

    check-cast v0, Lkotlin/coroutines/Continuation;

    return-object v0
.end method

.method public bridge synthetic invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .registers 3

    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    check-cast p2, Lkotlin/coroutines/Continuation;

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1;->invoke(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

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

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    move-result-object p0

    check-cast p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;

    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    invoke-virtual {p0, p1}, Lcom/example/data/network/LocalMovieBoxServer$start$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .registers 12

    const-string v1, "LocalMovieBoxServer"

    const-string v0, "Server started on 127.0.0.1:"

    iget-object v2, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->L$0:Ljava/lang/Object;

    move-object v3, v2

    check-cast v3, Lkotlinx/coroutines/CoroutineScope;

    invoke-static {}, Lkotlin/coroutines/intrinsics/IntrinsicsKt;->getCOROUTINE_SUSPENDED()Ljava/lang/Object;

    .line 49
    iget v2, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->label:I

    if-nez v2, :cond_8e

    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 51
    :try_start_13
    iget-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    new-instance v2, Ljava/net/ServerSocket;

    invoke-direct {v2}, Ljava/net/ServerSocket;-><init>()V

    iget-object v4, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    const/4 v9, 0x1

    .line 52
    invoke-virtual {v2, v9}, Ljava/net/ServerSocket;->setReuseAddress(Z)V

    .line 53
    new-instance v5, Ljava/net/InetSocketAddress;

    # getter for: Lcom/example/data/network/LocalMovieBoxServer;->port:I
    invoke-static {v4}, Lcom/example/data/network/LocalMovieBoxServer;->access$getPort$p(Lcom/example/data/network/LocalMovieBoxServer;)I

    move-result v4

    invoke-direct {v5, v4}, Ljava/net/InetSocketAddress;-><init>(I)V

    check-cast v5, Ljava/net/SocketAddress;

    invoke-virtual {v2, v5}, Ljava/net/ServerSocket;->bind(Ljava/net/SocketAddress;)V

    .line 51
    invoke-static {p1, v2}, Lcom/example/data/network/LocalMovieBoxServer;->access$setServerSocket$p(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/ServerSocket;)V

    .line 55
    iget-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    # getter for: Lcom/example/data/network/LocalMovieBoxServer;->port:I
    invoke-static {p1}, Lcom/example/data/network/LocalMovieBoxServer;->access$getPort$p(Lcom/example/data/network/LocalMovieBoxServer;)I

    move-result p1

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2, v0}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v1, p1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 56
    :goto_47
    invoke-static {v3}, Lkotlinx/coroutines/CoroutineScopeKt;->isActive(Lkotlinx/coroutines/CoroutineScope;)Z

    move-result p1

    if-eqz p1, :cond_8b

    iget-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    # getter for: Lcom/example/data/network/LocalMovieBoxServer;->isRunning:Z
    invoke-static {p1}, Lcom/example/data/network/LocalMovieBoxServer;->access$isRunning$p(Lcom/example/data/network/LocalMovieBoxServer;)Z

    move-result p1

    if-eqz p1, :cond_8b

    .line 57
    iget-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    # getter for: Lcom/example/data/network/LocalMovieBoxServer;->serverSocket:Ljava/net/ServerSocket;
    invoke-static {p1}, Lcom/example/data/network/LocalMovieBoxServer;->access$getServerSocket$p(Lcom/example/data/network/LocalMovieBoxServer;)Ljava/net/ServerSocket;

    move-result-object p1

    if-eqz p1, :cond_8b

    invoke-virtual {p1}, Ljava/net/ServerSocket;->accept()Ljava/net/Socket;

    move-result-object p1

    if-nez p1, :cond_64

    goto :goto_8b

    .line 58
    :cond_64
    invoke-virtual {p1, v9}, Ljava/net/Socket;->setTcpNoDelay(Z)V

    const/high16 v0, 0x10000

    .line 59
    invoke-virtual {p1, v0}, Ljava/net/Socket;->setSendBufferSize(I)V

    .line 60
    invoke-virtual {p1, v0}, Ljava/net/Socket;->setReceiveBufferSize(I)V

    .line 61
    new-instance v0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;

    iget-object v2, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    const/4 v4, 0x0

    invoke-direct {v0, v2, p1, v4}, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;-><init>(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;Lkotlin/coroutines/Continuation;)V

    move-object v6, v0

    check-cast v6, Lkotlin/jvm/functions/Function2;

    const/4 v7, 0x3

    const/4 v8, 0x0

    const/4 v4, 0x0

    const/4 v5, 0x0

    invoke-static/range {v3 .. v8}, Lkotlinx/coroutines/BuildersKt;->launch$default(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;ILjava/lang/Object;)Lkotlinx/coroutines/Job;
    :try_end_81
    .catch Ljava/lang/Exception; {:try_start_13 .. :try_end_81} :catch_82

    goto :goto_47

    :catch_82
    move-exception v0

    move-object p0, v0

    .line 66
    const-string p1, "Error in server socket loop"

    check-cast p0, Ljava/lang/Throwable;

    invoke-static {v1, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 68
    :cond_8b
    :goto_8b
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    return-object p0

    .line 49
    :cond_8e
    new-instance p0, Ljava/lang/IllegalStateException;

    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
