import pygame
from pygame import mixer
import random
import math

pygame.init()
window = pygame.display.set_mode((800, 600))

pygame.display.set_caption("shooter")
# ICON
icon = pygame.image.load('tank (1).png')
pygame.display.set_icon(icon)
# background sound
#mixer.music.load('New recording 1.wav')
#mixer.music.play(-1)
# PLAYER IMAGE
playerimg = pygame.image.load('space-invaders.png')
playx = 380
playy = 480
playxc = 0
playyc = 0
# ENEMY IMAGE
enemyimg = []
enemyx = []
enemyy = []
enemyxc = []
enemyyc = []
NE = 6
for i in range(NE):
    enemyimg.append(pygame.image.load('virus.png'))
    enemyx.append(random.randint(0, 730))
    enemyy.append(random.randint(50, 400))
    enemyxc.append(2)
    enemyyc.append(10)

# bulletimage
bulletimg = pygame.image.load('droplet.png')
bulletx = 0
bullety = 480
bulletxc = 0
bulletyc = 1.5
bullet_state = "ready"  # ready state means u can see the bullet on screeen

# score
score_value = 0
font = pygame.font.Font('freesansbold.ttf', 32)
textx = 10
texty = 10

font1 = pygame.font.Font('freesansbold.ttf', 64)
textx1 = 10
texty1 = 10


# player image draw fxn
def player(x, y):
    window.blit(playerimg, (x, y))


# bullet image draw fxn
def fire(x, y):
    global bullet_state
    bullet_state = "fire"
    window.blit(bulletimg, (x + 16, y + 10))


# enemy image draw fxn
def enemy(x, y, i):
    window.blit(enemyimg[i], (x, y))


# collision fxn
def isCollision(enemyx, enemyy, bulletx, bullety):
    distance = math.sqrt((math.pow(enemyx - bulletx, 2)) + (math.pow(enemyy - bullety, 2)))
    if distance < 27:
        return True
    else:
        return False


# score fxn
def scoredis(x, y):
    score = font.render("score:" + str(score_value), True, (255, 0, 0))
    window.blit(score, (x, y))


# gameover fxn
def game_over(x, y):
    gameover = font.render("GAME OVER", True, (255, 0, 0))
    window.blit(gameover, (x,y))


# GAMELOOP
run = True
while run:
    window.fill((50, 30, 20))
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
            # keystroke event
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_LEFT:
                playxc = -2
            if event.key == pygame.K_RIGHT:
                playxc += 2
            if event.key == pygame.K_UP:
                playyc = -2
            if event.key == pygame.K_DOWN:
                playyc = 2
            if event.key == pygame.K_SPACE:

                if bullet_state is "ready":
                    bulletx = playx
                    bulletsound = mixer.Sound('bomb.wav')
                    bulletsound.play()
                    fire(bulletx, bullety)


        if event.type == pygame.KEYUP:
            if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT or event.key == pygame.K_UP or event.key == pygame.K_DOWN:
                playxc = 0
                playyc = 0
    playx += playxc
    playy += playyc

    # player boundaries
    if playx <= 0:
        playx = 0
    elif playx >= 736:
        playx = 736

    if playy <= 0:
        playy = 0
    elif playy >= 536:
        playy = 536
    # enemy boundaries
    for i in range(NE):
        # game over
        if enemyy[i] > 550:
            for j in range(NE):
                enemyy[j] = 2000
            game_over(325,300)
            break
        enemyx[i] += enemyxc[i]
        if enemyx[i] <= 0:
            enemyxc[i] = 1
            enemyy[i] += enemyyc[i]
        elif enemyx[i] >= 736:
            enemyxc[i] = -1
            enemyy[i] += enemyyc[i]
        # collision
        collision = isCollision(enemyx[i], enemyy[i], bulletx, bullety)
        if collision:
            bulletsound1 = mixer.Sound('bomb.wav')
            bulletsound1.play()
            bullety = 480
            bullet_state = "ready"
            score_value += 1

            enemyx[i] = random.randint(0, 780)
            enemyy[i] = random.randint(50, 450)
        enemy(enemyx[i], enemyy[i], i)

    # bullet movement
    if bullety <= 0:
        bullety = 480
        bullet_state = "ready"

    if bullet_state is "fire":
        fire(bulletx, bullety)
        bullety -= bulletyc

    scoredis(textx, texty)
    player(playx, playy)
    pygame.display.update()
