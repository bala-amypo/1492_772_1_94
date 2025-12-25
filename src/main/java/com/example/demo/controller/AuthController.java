@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        UserDTO created = userService.registerUser(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserLoginDTO dto) {
        UserDTO user = userService.login(dto);
        return ResponseEntity.ok(user);
    }
}
