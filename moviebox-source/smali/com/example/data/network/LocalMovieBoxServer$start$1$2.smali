.class final Lcom/example/data/network/LocalMovieBoxServer$start$1$2;
.super Lkotlin/coroutines/jvm/internal/SuspendLambda;
.source "LocalMovieBoxServer.kt"

# interfaces
.implements Lkotlin/jvm/functions/Function2;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/data/network/LocalMovieBoxServer$start$1;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
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
    c = "com.example.data.network.LocalMovieBoxServer$start$1$2"
    f = "LocalMovieBoxServer.kt"
    i = {}
    l = {
        0x3e
    }
    m = "invokeSuspend"
    n = {}
    s = {}
.end annotation


# instance fields
.field final synthetic $socket:Ljava/net/Socket;

.field label:I

.field final synthetic this$0:Lcom/example/data/network/LocalMovieBoxServer;


# direct methods
.method constructor <init>(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;Lkotlin/coroutines/Continuation;)V
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/example/data/network/LocalMovieBoxServer;",
            "Ljava/net/Socket;",
            "Lkotlin/coroutines/Continuation<",
            "-",
            "Lcom/example/data/network/LocalMovieBoxServer$start$1$2;",
            ">;)V"
        }
    .end annotation

    iput-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    iput-object p2, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->$socket:Ljava/net/Socket;

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

    new-instance p1, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;

    iget-object v0, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    iget-object p0, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->$socket:Ljava/net/Socket;

    invoke-direct {p1, v0, p0, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;-><init>(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;Lkotlin/coroutines/Continuation;)V

    check-cast p1, Lkotlin/coroutines/Continuation;

    return-object p1
.end method

.method public bridge synthetic invoke(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    .registers 3

    check-cast p1, Lkotlinx/coroutines/CoroutineScope;

    check-cast p2, Lkotlin/coroutines/Continuation;

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->invoke(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

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

    invoke-virtual {p0, p1, p2}, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->create(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;

    move-result-object p0

    check-cast p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;

    sget-object p1, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    invoke-virtual {p0, p1}, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method public final invokeSuspend(Ljava/lang/Object;)Ljava/lang/Object;
    .registers 6

    invoke-static {}, Lkotlin/coroutines/intrinsics/IntrinsicsKt;->getCOROUTINE_SUSPENDED()Ljava/lang/Object;

    move-result-object v0

    .line 61
    iget v1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->label:I

    const/4 v2, 0x1

    if-eqz v1, :cond_17

    if-ne v1, v2, :cond_f

    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    goto :goto_2a

    :cond_f
    new-instance p0, Ljava/lang/IllegalStateException;

    const-string p1, "call to \'resume\' before \'invoke\' with coroutine"

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0

    :cond_17
    invoke-static {p1}, Lkotlin/ResultKt;->throwOnFailure(Ljava/lang/Object;)V

    .line 62
    iget-object p1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->this$0:Lcom/example/data/network/LocalMovieBoxServer;

    iget-object v1, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->$socket:Ljava/net/Socket;

    move-object v3, p0

    check-cast v3, Lkotlin/coroutines/Continuation;

    iput v2, p0, Lcom/example/data/network/LocalMovieBoxServer$start$1$2;->label:I

    # invokes: Lcom/example/data/network/LocalMovieBoxServer;->handleClient(Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;
    invoke-static {p1, v1, v3}, Lcom/example/data/network/LocalMovieBoxServer;->access$handleClient(Lcom/example/data/network/LocalMovieBoxServer;Ljava/net/Socket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;

    move-result-object p0

    if-ne p0, v0, :cond_2a

    return-object v0

    .line 63
    :cond_2a
    :goto_2a
    sget-object p0, Lkotlin/Unit;->INSTANCE:Lkotlin/Unit;

    return-object p0
.end method
