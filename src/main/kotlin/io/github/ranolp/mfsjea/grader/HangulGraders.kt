package io.github.ranolp.mfsjea.grader


private fun countRegex(s: String, regex: Regex): Int = regex.findAll(s).map { it.value.length }.sum()

/**
 * This class computes the sentence by count of contained hangul syllable(version 1.0, 2350 characters) block's characters.
 */
object Hangul2350Grader : SentenceGrader {
    private val REGEX =
        "[가-각간갇-갊감-갗같-객갠갤갬-갭갯-갱갸-갹갼걀걋걍걔걘걜거-걱건걷-걸걺검-겁것-겆겉-게겐겔겜-겝겟-겡겨-겪견겯-결겸-겹겻-경곁계곈곌곕곗고-곡곤곧-골곪곬곯-곱곳공-곶과-곽관괄괆괌-괍괏광괘괜괠괩괬-괭괴-괵괸괼굄-굅굇굉교굔굘굡굣구-국군굳-굶굻-굽굿궁-궂궈-궉권궐궜-궝궤궷귀-귁귄귈귐-귑귓규균귤그-극근귿-긁금-급긋긍긔기-긱긴긷-길긺김-깁깃깅-깆깊까-깎깐깔깖깜-깝깟-깡깥깨-깩깬깰깸-깹깻-깽꺄-꺅꺌꺼-꺾껀껄껌-껍껏-껑께-껙껜껨껫껭껴껸껼꼇-꼈꼍꼐꼬-꼭꼰꼲꼴꼼-꼽꼿꽁-꽃꽈-꽉꽐꽜-꽝꽤-꽥꽹꾀꾄꾈꾐-꾑꾕꾜꾸-꾹꾼꿀꿇-꿉꿋꿍-꿎꿔꿜꿨-꿩꿰-꿱꿴꿸뀀-뀁뀄뀌뀐뀔뀜-뀝뀨끄-끅끈끊끌끎끓-끕끗끙끝끼-끽낀낄낌-낍낏낑나-낚난낟-낢남-납낫-낯낱낳-낵낸낼냄-냅냇-냉냐-냑냔냘냠냥너-넉넋-넌널넒-넓넘-넙넛-넝넣-넥넨넬넴-넵넷-넹녀-녁년녈념-녑녔-녕녘녜녠노-녹논놀놂놈-놉놋농높-놔놘놜놨뇌뇐뇔뇜-뇝뇟뇨-뇩뇬뇰뇹뇻뇽누-눅눈눋-눌눔-눕눗눙눠눴눼뉘뉜뉠뉨-뉩뉴-뉵뉼늄-늅늉느-늑는늘-늚늠-늡늣능-늦늪늬늰늴니-닉닌닐닒님-닙닛닝닢다-닦단닫-닯닳-답닷-닻닿-댁댄댈댐-댑댓-댕댜더-덖던덛-덜덞-덟덤-덥덧덩덫덮데-덱덴델뎀-뎁뎃-뎅뎌뎐뎔뎠-뎡뎨뎬도-독돈돋-돌돎돐돔-돕돗동돛돝돠돤돨돼됐되된될됨-됩됫됴두-둑둔둘둠-둡둣둥둬뒀뒈뒝뒤뒨뒬뒵뒷뒹듀듄듈듐듕드-득든듣-들듦듬-듭듯등듸디-딕딘딛-딜딤-딥딧-딪따-딱딴딸땀-땁땃-땅땋-땍땐땔땜-땝땟-땡떠-떡떤떨떪-떫떰-떱떳-떵떻-떽뗀뗄뗌-뗍뗏-뗑뗘뗬또-똑똔똘똥똬똴뙈뙤뙨뚜-뚝뚠뚤뚫-뚬뚱뛔뛰뛴뛸뜀-뜁뜅뜨-뜩뜬뜯-뜰뜸-뜹뜻띄띈띌띔-띕띠띤띨띰-띱띳띵라-락란랄람-랍랏-랒랖-랙랜랠램-랩랫-랭랴-략랸럇량러-럭런럴럼-럽럿-렁렇-렉렌렐렘-렙렛렝려-력련렬렴-렵렷-령례롄롑롓로-록론롤롬-롭롯롱롸롼뢍뢨뢰뢴뢸룀-룁룃룅료룐룔룝룟룡루-룩룬룰룸-룹룻룽뤄뤘뤠뤼-뤽륀륄륌륏륑류-륙륜률륨-륩륫륭르-륵른를름-릅릇릉-릊릍-릎리-릭린릴림-립릿링마-막만많-맒맘-맙맛망-맞맡맣-맥맨맬맴-맵맷-맺먀-먁먈먕머-먹먼멀멂멈-멉멋멍-멎멓-멕멘멜멤-멥멧-멩며-멱면멸몃-명몇몌모-목몫-몬몰몲몸-몹못몽뫄뫈뫘-뫙뫼묀묄묍묏묑묘묜묠묩묫무-묶문묻-묾뭄-뭅뭇뭉뭍뭏-뭐뭔뭘뭡뭣뭬뮈뮌뮐뮤뮨뮬뮴뮷므믄믈믐믓미-믹민믿-밀밂밈-밉밋-밍및밑바-반받-밟밤-밥밧방밭배-백밴밸뱀-뱁뱃-뱅뱉뱌-뱍뱐뱝버-벅번벋-벌벎범-법벗벙-벚베-벡벤벧-벨벰-벱벳-벵벼-벽변별볍볏-병볕볘볜보-볶본볼봄-봅봇봉봐봔봤봬뵀뵈-뵉뵌뵐뵘-뵙뵤뵨부-북분붇-붊붐-붑붓붕붙-붚붜붤붰붸뷔-뷕뷘뷜뷩뷰뷴뷸븀븃븅브-븍븐블븜-븝븟비-빅빈빌빎빔-빕빗빙-빛빠-빡빤빨빪빰-빱빳-빵빻-빽뺀뺄뺌-뺍뺏-뺑뺘-뺙뺨뻐-뻑뻔뻗-뻘뻠뻣-뻥뻬뼁뼈-뼉뼘-뼙뼛-뼝뽀-뽁뽄뽈뽐-뽑뽕뾔뾰뿅뿌-뿍뿐뿔뿜뿟뿡쀼쁑쁘쁜쁠쁨-쁩삐-삑삔삘삠-삡삣삥사-삭삯-산삳-삶삼-삽삿-상샅새-색샌샐샘-샙샛-생샤-샥샨샬샴-샵샷샹섀섄섈섐섕서-선섣-설섦-섧섬-섭섯-성섶세-섹센셀셈-셉셋-셍셔-셕션셜셤-셥셧-셩셰셴셸솅소-솎손솔솖솜-솝솟송솥솨-솩솬솰솽쇄쇈쇌쇔쇗-쇘쇠쇤쇨쇰-쇱쇳쇼-쇽숀숄숌-숍숏숑수-숙순숟-술숨-숩숫숭숯숱-숲숴쉈쉐-쉑쉔쉘쉠쉥쉬-쉭쉰쉴쉼-쉽쉿슁슈-슉슐슘슛슝스-슥슨슬-슭슴-습슷승시-식신싣-실싫-십싯싱싶싸-싹싻-싼쌀쌈-쌉쌌-쌍쌓-쌕쌘쌜쌤-쌥쌨-쌩썅써-썩썬썰썲썸-썹썼-썽쎄쎈쎌쏀쏘-쏙쏜쏟-쏠쏢쏨-쏩쏭쏴-쏵쏸쐈쐐쐤쐬쐰쐴쐼-쐽쑈쑤-쑥쑨쑬쑴-쑵쑹쒀쒔쒜쒸쒼쓩쓰-쓱쓴쓸쓺쓿-씁씌씐씔씜씨-씩씬씰씸-씹씻씽아-악안-않알-앎앓-압앗-앙앝-앞애-액앤앨앰-앱앳-앵야-약얀얄얇얌-얍얏양얕얗-얘얜얠얩어-억언-얹얻-얾엄-엊엌엎에-엑엔엘엠-엡엣엥여-엮연열엶-엷염-영옅-예옌옐옘-옙옛-옜오-옥온올-옮옰옳-옵옷옹옻와-왁완왈왐-왑왓-왕왜-왝왠왬왯왱외-왹왼욀욈-욉욋욍요-욕욘욜욤-욥욧용우-욱운울-욺움-웁웃웅워-웍원월웜-웝웠-웡웨-웩웬웰웸-웹웽위-윅윈윌윔-윕윗윙유-육윤율윰-윱윳융윷으-윽은을읊음-읍읏응-의읜읠읨읫이-익인일-읾잃-입잇-잊잎자-작잔잖-잘잚잠-잡잣-잦재-잭잰잴잼-잽잿-쟁쟈-쟉쟌쟎쟐쟘쟝쟤쟨쟬저-적전절젊점-접젓정-젖제-젝젠젤젬-젭젯젱져젼졀졈-졉졌-졍졔조-족존졸졺좀-좁좃종-좇좋-좍좔좝좟좡좨좼-좽죄죈죌죔-죕죗죙죠-죡죤죵주-죽준줄-줆줌-줍줏중줘줬줴쥐-쥑쥔쥘쥠-쥡쥣쥬쥰쥴쥼즈-즉즌즐즘-즙즛증지-직진짇-질짊짐-집짓징-짖짙-짚짜-짝짠짢짤짧짬-짭짯-짱째-짹짼쨀쨈-쨉쨋-쨍쨔쨘쨩쩌-쩍쩐쩔쩜-쩝쩟-쩡쩨쩽쪄쪘쪼-쪽쫀쫄쫌-쫍쫏쫑쫓쫘-쫙쫠쫬쫴쬈쬐쬔쬘쬠-쬡쭁쭈-쭉쭌쭐쭘-쭙쭝쭤쭸-쭹쮜쮸쯔쯤쯧쯩찌-찍찐찔찜-찝찡-찢찧-착찬찮찰참-찹찻-찾채-책챈챌챔-챕챗-챙챠챤챦챨챰챵처-척천철첨-첩첫-청체-첵첸첼쳄-쳅쳇쳉쳐쳔쳤쳬쳰촁초-촉촌촐촘-촙촛총촤촨촬촹최쵠쵤쵬-쵭쵯쵱쵸춈추-축춘출춤-춥춧충춰췄췌췐취췬췰췸-췹췻췽츄츈츌츔츙츠-측츤츨츰-츱츳층치-칙친칟-칡침-칩칫칭카-칵칸칼캄-캅캇캉캐-캑캔캘캠-캡캣-캥캬-캭컁커-컥컨컫-컬컴-컵컷-컹케-켁켄켈켐-켑켓켕켜켠켤켬-켭켯-켱켸코-콕콘콜콤-콥콧콩콰-콱콴콸쾀쾅쾌쾡쾨쾰쿄쿠-쿡쿤쿨쿰-쿱쿳쿵쿼퀀퀄퀑퀘퀭퀴-퀵퀸퀼큄-큅큇큉큐큔큘큠크-큭큰클큼-큽킁키-킥킨킬킴-킵킷킹타-탁탄탈-탉탐-탑탓-탕태-택탠탤탬-탭탯-탱탸턍터-턱턴털턺텀-텁텃-텅테-텍텐텔템-텝텟텡텨텬텼톄톈토-톡톤톨톰-톱톳통톺톼퇀퇘퇴퇸툇툉툐투-툭툰툴툼-툽툿퉁퉈퉜퉤튀-튁튄튈튐-튑튕튜튠튤튬튱트-특튼튿-틀틂틈-틉틋틔틘틜틤-틥티-틱틴틸팀-팁팃팅파-팎판팔팖팜-팝팟-팡팥패-팩팬팰팸-팹팻-팽퍄-퍅퍼-퍽펀펄펌-펍펏-펑페-펙펜펠펨-펩펫펭펴편펼폄-폅폈-평폐폘폡폣포-폭폰폴폼-폽폿퐁퐈퐝푀푄표푠푤푭푯푸-푹푼푿-풀풂품-풉풋풍풔풩퓌퓐퓔퓜퓟퓨퓬퓰퓸퓻퓽프픈플픔-픕픗피-픽핀필핌-핍핏핑하-학한할핥함-합핫항해-핵핸핼햄-햅햇-행햐향허-헉헌헐헒험-헙헛헝헤-헥헨헬헴-헵헷헹혀-혁현혈혐-협혓-형혜혠혤혭호-혹혼홀홅홈-홉홋홍홑화-확환활홧황홰-홱홴횃횅회-획횐횔횝횟횡효횬횰횹횻후-훅훈훌훑훔훗훙훠훤훨훰훵훼-훽휀휄휑휘-휙휜휠휨-휩휫휭휴-휵휸휼흄흇흉흐-흑흔흖-흙흠-흡흣흥흩희흰흴흼-흽힁히-힉힌힐힘-힙힛힝]".toRegex()

    override fun computeScore(sentence: String): Int = countRegex(sentence, REGEX) * 10
}

/**
 * This class computes the sentence by counting frequencies of each hangul syllables.
 */
object HangulFrequencyGrader : SentenceGrader {
    private val FREQUENCY_ORDER =
            "이다의는에을하한고가로기지사서은도를대정리자수시으있어구인나제국과그해전부것일적아연라성들상원여보장화주소동공조스경계용위우게학만개면되관문유선중산치신회발비분생내방무와세니물등할실통었미모러업교체진재안야명민간며단당요년거마금된오본했법합식없각였결영행때데력반설터려속운양현차종말형음술석바입역임않작히및건질표외강두까백권트르직불호심따처타태출파천남람던점감저난후포또특최크달예같능변북드프래책김노함박배추환열평증매울품약집군향근알초온급목더료른론확준토록활련격월광판키청습험번절류규루복량많피새레응받령란날편못항살았검측필망독린효립언육올축뉴느투병런름철협디든총될극담족억버순농별삼쟁율범길늘템져존접색너황막앞승침럼충돌찰림써송카손악씨코높참탄채렇창견쓰택잘겠줄폭워잡획균밀논누머겨갖염갈압쪽벌착완랑께희걸층큰친밝취액핵놓컴맞테졌허메네쳐왔베곡움혁왕뿐퓨글탈풍곳켜눈락뒤암좋밖애얼귀먹혼돼덕끝촉긴얻혀패폐죽럽째엔례홍됐티볼익득떤돈헌힘페찾윤끼융뜻델듯몇므났봉죄욱닌냐념훈님징둘풀엄웨넘률브떻즘칼커떠슬골틀빠값굴몰땅칙찬섬엇퇴몸탁녀케괴플봅팔싸렸앙객욕답롯숙릴략퍼즈싶왜곧흥좌꾸큼깨클떨킨센혹낮혔냉팀낸궁붙흡탕첫램옥척첨잠빈벽놀먼흐짐혈즉칠십널꽃픽턴섭닐션휴잔링씩갔곤휘끌짓킬넓맥믿칭납낙웃깊멀탐렬옛쇄렵갑넣쉽랜촌둔슨흔숨뢰녹뿌뛰혜묘짜펴읍헤쌀뇌콘밤켰룹춘맡텔빛벼읽벗블빨싱맹흘폴힌콜졸뜨듣쉬냈쿠딸잎쇠닥흑엘둥캐젊롭앉밑렌깔콩셈륙옷빌톱좀웅쌍묻웠츠잃벨엽됨쯤렀멸찍쳤혐솔푸첩슴붕잇랐닭빚묵톤끄찌꼭즐쓸뒷듭슈럴겪맛낼옮탑쓴몽겼빼핀룩슷늄홀꺼셨랄짝냥걱릇듬늦낭굳컨삭튼꼬쥐핑랍밥꿈삶섯낌팅씀겁봄겸긍쌓팽섰낳싼똑룡털숭컬곱덤셀끊짧멘끗맺렴옳흰럭뽑뮬씬얘젠끔맨뀌덜믹멍밭뚜텐횡끈잉잊봐꼴훨틸흉좁닫앗옆빙띠놈춤캠숫옹겹셋몬괄롤텍붉덩셔낱흙젖썼궤탓뷰꾼팩렷덴뼈랫겉쾌엑엉춰듈눌릭덮벤젤쁜섞륨칸딩떼냄깃닷룬긋훌릉틱묶쇼꼽녕곰펼힐쫓폰쏟렁쁘퀴껴칩덧틈륭톨룰갱옐둑뤄롱걷딱몫펜빗렉닉쩌떡훼굽밟곽돋왼닝펄댐싫윈뭐늬겐밍젓틴넷컫돕꾀픈낀턱뚫롬녁탱껍륜썩큐닿솟껏땐햇짚슘킴킹멋삽뉘픔씻싹펙띄켈샤헬딘끓릿뻗뭇뚝콤푼깝얽맑펌뭣캄꿔곁됩랭숲짙뇨칫줌튀벡랙팎떳샘깎찮켓핫뻔엿땀늙뿔컷윗쩔꿀뉜뜯쩍멈딴뚱죠궐팡헐앤짖쏘뜸랬꽂굵닦얀젝흠릅웬릎잖갓잭낄텅챙꺾굶헝딪랴핍낚멜셰붓띤앓줘닙녔볍딜깥솜킷넉엮컸뭉캔핏헨뮤얇닮몹멕잦봤씌촬휩빵뻐얹쑤겔핸엎쑥넥흩깜빔낡댄벅뭔밸뜩뜬낫밴렘갯옴갚캘봇튜팬펠걀젼뱃훔굿홉삐뱀뜰붐헛툼촛옵읊꽤툴듐깡낯렛잣쁨톡괘찔셧늠쪼썽쏠꿰솥갇꼈벙슐괜귤찢쿄콕쯔홈앵엌컵샌꽁눠뤼쥴앨볕늑싣횟썰퉁숱뱅룸얕즙둠웰뀐폼뿜듀븐륵엊둬맙덟룻풋뽕슭넬쨌셜줍짠넌줬꼼팥똥젯팍뭄뾰쭉랩텝뛴눗껑긁쿨럿뺀섹펀굉댁덱탠깐냇왈댕빅퍽뎀콧엷늪둡짱댈셉씹햄윌맘쥬쿵얄뮴깍댔엠폈삿뱉볏섣샀펩랗썬깅쭈갤츰궂쌈맵쿼췌돔넨돗켄꿩캡뭘탔뀔댓쉴툭덥꿇묽춧씁렐튿샐갸닛멎딛휠훑싯튬맴넋밋쬐킥휨샅팜잿빤쩡첼돛쉘탤훗숯콥찹뺄엣빽꽉늉촘찡밉춥꺽짤퐁볶푹녘냅헥껄멧탬맷쥔멱맏딕겊닳딧찧줏뺏곶윽깁눕썹좇뷔쉰샬쎄톰벳뺨뀜샴얗홑텃훤뜀멤팝켤앰짊쩐땜챔툰갠벚쁠웍뚤펑찼뎌슛퀘앎텀팁윙밧떴쐐텁칵똘뮌믐엾껌숟깻쏜깬멩놔챈찜뵈탭삯퓰렙핥뒹뽐벵맣귓귄잼숍놋뛸뎅왁췄튕잴밈꾹탉랏퀀콰겟찐얌갛샹뻘뺑꿍빴룽엥옻뽀빡뻑즌왠띨쉐텨곪펫팠눅혓닻얏꽝횃뤘웁섶괭쵸넙셸믈띈듦컥뗏잰쭐렝뼉츄끽쪄켠뎃눔잽딤쇳캉윷뻤땄낟슁꿋쓱뿍쉼찻휜큘뼛틔펭땡뫼휙맬뗀쟈갉쾅냘톈꼰씸퀸꿨촐헷갭헵겅땔솝덫챌꽹랠콸짭옅뵙둣뒀헴뻣깟뻥힙핌댑샛쏴갗뺌섀쿤뇽빻칡첸짢쳇옇쿡팻챗샨괸햐잤휼폿멥쏭챠텄깰궈뗄홧끙홋괌갹쐬꿎꽈헉곬솎꿉쑨컹왓슥깼볐닢캬숴넴탯폄힉퉈겋핼앴숀킵벰뮈펐흄뼘큽놨켐훅넝헹꼇맸헙튤꼐쌌넛틂빳뭏쥘쯧푯뎠웜꼿겻씰쭝쾰쫑띔몄캅됫퀵셍챘숄쩨뙈옭굼벧앳눴쁩옌떫윅쨋욘쨍껐쌔셴씽쇽뷴췹촨떵튈낍틋넜햅귐뭍뺐퓌죈넸댜긷깽믓샵빰긱땋벋짇븀픕쿰엡샷쳬톳쎈읜훠돠쌘쾨짬쑈믄죤펨푄뵐욜뷸낵휫먀냠츨땠쳄뱌죔삥젬홰욥켕뭡겆꾐빕쭙귈걔뵌콱곯뙤쉥셩켯짹쑹롸앱셌딥쏙졍곗쳉붑팰뎨놉죌띌쬘됴쩝흗뱄텟쫙쥰붇촤쟤꾜킁쪘읠졔묄쌩켁끕쫀쇤툐뗐풂쳔쎌얜팸숌뵤캥넒뮐캤퓐뜹껀탸콴갬멓섧쾡짰쉈휀뢴툇솨굄묏냑썅윔뗑읗퍄뉼찝숑쟀좡땟쬔뼁갰웩뒈샜웹삔녈롼눼횐윰쌉숩퀄삑퀭돤숏녜퀼챤챨겜뱁앝쭤낑읕멉얍훙띕첵켬슝꿜휑켸힛닯셤왬봬퓸윳귿쒸팼껸땃겄욤삣욧돎껭읒읓꿴흴뎡뻬휄눙팟꼍똬읔쐰팹읖쌕쥼츌뎬옜튄꾄훽띵꽥쒜쩜켑뇔쟌녑뮨쉿뇰볘궜쫄돐뎐솬먕텡캇텬텼덖롄캑쬠똔흽뽄냔읏왝긔쉔졈겡괼꺅웝홱펏좽첬뇩횔돨쟎퉤쟝깩엶죵궉빪뀨땁뤽륀낏훰왹쨈듸욀겯쨔캣뷜닺띰샥옘퀑뉠뗌럇뫄팖풔룀뎁룔챕큠퓽뉨큭뵀쫏눋췻곌쫘벱좆퇘츤힝짼윕쨉룐솽샙놂튱걋젱뀄쵤쿱쿳굇틉졀윱틘싻뻠밌묜괵붜붸꾕큄큇뗘좃껜벴옰쌜츙쌤좟뼝쭁좨깠쭌놘쭘늚섦븍퍅쮸걘읨뭅뿅랸늡넵쏀륄빎왱쉠쀼줴멨쥑쏩욈쥠폅롑돝뇐잗걍뒬쐼몃뇜딨퐈푀꺄쑬푠쑴몌쒀뎔챵넹톄쒼휸쓩뱍풩뱐삵켭뱝쓿웡톺톼쟨덛뜁뵘깆흼뢸뵨짯랒툿먈뺘퉜랖"

    override fun computeScore(sentence: String): Int = sentence
            .map { c -> (FREQUENCY_ORDER.length - FREQUENCY_ORDER.indexOf(c)).toFloat() / FREQUENCY_ORDER.length }
            .map { if(it > 1f) 0f else it }
            .sumByDouble { it * 3.0 }.toInt()
}

/**
 * This class computes the sentence by count of well-converted number or mathematical expressions.
 */
object NumberGrader : SentenceGrader {
    private val REGEX = "[0-9,.+\\-*/%]{2,}".toRegex()

    override fun computeScore(sentence: String): Int = countRegex(sentence, REGEX)
}

/**
 * This class computes the sentence by count of matched brackets' count.
 */
object ParenthesisGrader : SentenceGrader {
    private val REGEX = "\\(.+\\)".toRegex()

    override fun computeScore(sentence: String): Int = countRegex(sentence, REGEX) * 10
}

/**
 * This class computes the sentence by count of incomplete hangul letter
 */
object IncompleteWordGrader : SentenceGrader {
    private val REGEX = "[ᄀ-하-ᅵᆨ-ᇂㄱ-ㅎㅏ-ㅣᄀ-ᄒᆨ-ᇂ]".toRegex()
    override fun computeScore(sentence: String): Int = countRegex(sentence, REGEX) * -10
}
