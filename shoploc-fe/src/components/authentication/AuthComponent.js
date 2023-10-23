import './AuthComponent.css';

const AuthComponent = () => {

  return (
        <div id='auth-container'>
            <form id='auth-form'>
                <input className="auth-login" id="email" type="text" placeholder="Adresse e-mail ou nom d'utilisateur" autoFocus="1" aria-label="Adresse e-mail ou nom d'utilisateur"/>
                <input className="auth-login" id="password" type="password" placeholder="Mot de passe" autoFocus="1" aria-label="Mot de passe"/>
                <button className="auth-login" value="1" name="login" type="submit"> Se connecter </button>     
            </form>
            
            <a href="à completer " id='auth-apassword'>Mot de passe oublié ?</a>

            <div className="auth-line"></div>
            
            <a role="button" id="auth-create"> Créer un nouveau compte</a>
            
            <a id='auth-arestpassword' href=""> Faire ma demande de création d'un compte commerçant</a>
        </div>
  );
};

export default AuthComponent;
